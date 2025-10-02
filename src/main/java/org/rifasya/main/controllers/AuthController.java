package org.rifasya.main.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.rifasya.main.components.JwtUtil;
import org.rifasya.main.dto.request.LoginRequest;
import org.rifasya.main.dto.request.RegisterRequest;
import org.rifasya.main.dto.response.JwtResponseDTO;
import org.rifasya.main.services.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String token = authService.login(request.getUsernameOrEmail(), request.getPassword());

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true); // 🔒 No accesible desde JS
        cookie.setSecure(true);   // 🔒 Solo por HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1 hora

        response.addCookie(cookie);

        return ResponseEntity.ok("Login correcto");
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0) // expira de inmediato
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "Logout exitoso"));
    }


}

