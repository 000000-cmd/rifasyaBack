package org.rifasya.main.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rifasya.main.components.JwtUtil;
import org.rifasya.main.dto.request.LoginRequest;
import org.rifasya.main.dto.response.LoginResponseDTO;
import org.rifasya.main.entities.RefreshToken;
import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import org.rifasya.main.mappers.UserMapper;
import org.rifasya.main.repositories.ThirdPartyRepository;
import org.rifasya.main.repositories.UserRepository;
import org.rifasya.main.services.AuthService;
import org.rifasya.main.services.RefreshTokenService;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final ThirdPartyRepository thirdPartyRepository;
    private final UserRepository userRepository;
    private final BuildProperties buildProperties;


    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtUtil jwtUtil, UserMapper userMapper, ThirdPartyRepository thirdPartyRepository, UserRepository userRepository, BuildProperties buildProperties) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.thirdPartyRepository = thirdPartyRepository;
        this.userRepository = userRepository;
        this.buildProperties = buildProperties;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        // 1. Validar credenciales y obtener el usuario
        User user = authService.login(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());

        // 2. Generar el AccessToken de corta duración
        String accessToken = jwtUtil.generateToken(user.getUsername());

        // 3. Crear (o recrear) el RefreshToken de larga duración en la BD
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        // 4. Crear la cookie HttpOnly EXCLUSIVAMENTE para el RefreshToken
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken.getToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // Cambiar a true en producción (HTTPS)
        refreshTokenCookie.setPath("/");
        long duration = refreshToken.getExpiryDate().getEpochSecond() - Instant.now().getEpochSecond();
        refreshTokenCookie.setMaxAge((int) duration);
        response.addCookie(refreshTokenCookie);

        // 5. Preparar el DTO de respuesta con los datos del usuario y el AccessToken
        Optional<ThirdParty> thirdPartyOpt = thirdPartyRepository.findByUser(user);
        LoginResponseDTO responseDTO = userMapper.toLoginResponseDTO(user, thirdPartyOpt.orElse(null));
        responseDTO.setAccessToken(accessToken); // Se añade el AccessToken al cuerpo del JSON

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return ResponseEntity.status(401).body(Map.of("message", "No refresh token cookie found"));
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .flatMap(refreshTokenService::findByToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newAccessToken = jwtUtil.generateToken(user.getUsername());
                    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
                })
                .orElse(ResponseEntity.status(401).body(Map.of("message", "Invalid refresh token")));
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponseDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en el token"));

        Optional<ThirdParty> thirdPartyOpt = thirdPartyRepository.findByUser(user);
        LoginResponseDTO responseDTO = userMapper.toLoginResponseDTO(user, thirdPartyOpt.orElse(null));

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            Arrays.stream(request.getCookies())
                    .filter(cookie -> "refreshToken".equals(cookie.getName()))
                    .findFirst()
                    .ifPresent(cookie -> refreshTokenService.deleteByToken(cookie.getValue()));
        }

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Logout exitoso"));
    }

    /**
     * Endpoint que devuelve la versión actual de la aplicación.
     * Responde a GET /auth/apiV
     * @return Un objeto JSON con la versión, ej: {"version": "0.0.1-SNAPSHOT"}
     */
    @GetMapping("/apiV")
    public Map<String, String> getApiVersion() {
        // Creamos un mapa para devolver una respuesta JSON estructurada
        Map<String, String> response = new HashMap<>();

        // Obtenemos la versión desde el objeto BuildProperties
        response.put("version", buildProperties.getVersion());

        return response;
    }
}

