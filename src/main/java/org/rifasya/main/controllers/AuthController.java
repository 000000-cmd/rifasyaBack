package org.rifasya.main.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.rifasya.main.components.JwtUtil;
import org.rifasya.main.dto.request.LoginRequest;
import org.rifasya.main.dto.response.LoginResponseDTO;
import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import org.rifasya.main.mappers.UserMapper;
import org.rifasya.main.repositories.ThirdPartyRepository;
import org.rifasya.main.repositories.UserRepository;
import org.rifasya.main.services.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ThirdPartyRepository thirdPartyRepository;

    public AuthController(AuthService authService, JwtUtil jwtUtil, UserMapper userMapper,
                          UserRepository userRepository, ThirdPartyRepository thirdPartyRepository) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.thirdPartyRepository = thirdPartyRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String token = authService.login(request.getUsernameOrEmail(), request.getPassword());

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Cambiar a true en producción (HTTPS)
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); // 1 día
        response.addCookie(cookie);

        User user = userRepository.findByUser(request.getUsernameOrEmail())
                .or(() -> userRepository.findByMail(request.getUsernameOrEmail()))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Optional<ThirdParty> thirdPartyOpt = thirdPartyRepository.findByUser(user);
        LoginResponseDTO responseDTO = userMapper.toLoginResponseDTO(user, thirdPartyOpt.orElse(null));

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponseDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        User user = userRepository.findByUser(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Optional<ThirdParty> thirdPartyOpt = thirdPartyRepository.findByUser(user);
        LoginResponseDTO responseDTO = userMapper.toLoginResponseDTO(user, thirdPartyOpt.orElse(null));

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expira inmediatamente
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Logout exitoso"));
    }
}

