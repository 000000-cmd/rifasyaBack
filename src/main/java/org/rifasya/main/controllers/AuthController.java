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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtUtil jwtUtil, UserMapper userMapper, ThirdPartyRepository thirdPartyRepository, UserRepository userRepository) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.thirdPartyRepository = thirdPartyRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        User user = authService.login(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
        String accessToken = jwtUtil.generateToken(user.getUser());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken.getToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // true en producción
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) (refreshToken.getExpiryDate().getEpochSecond() - (System.currentTimeMillis() / 1000)));
        response.addCookie(refreshTokenCookie);

        Optional<ThirdParty> thirdPartyOpt = thirdPartyRepository.findByUser(user);
        LoginResponseDTO responseDTO = userMapper.toLoginResponseDTO(user, thirdPartyOpt.orElse(null));
        responseDTO.setAccessToken(accessToken);

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
                    String newAccessToken = jwtUtil.generateToken(user.getUser());
                    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
                })
                .orElse(ResponseEntity.status(401).body(Map.of("message", "Invalid refresh token")));
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponseDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        User user = userRepository.findByUser(userDetails.getUsername())
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
}

