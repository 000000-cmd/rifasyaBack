package org.rifasya.main.services;

import org.rifasya.main.components.JwtUtil;
import org.rifasya.main.dto.request.LoginRequest;
import org.rifasya.main.entities.User;
import org.rifasya.main.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    public String login(String usernameOrEmail, String password) {
        User user = userRepository.findByUser(usernameOrEmail)
                .or(() -> userRepository.findByMail(usernameOrEmail))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return jwtUtil.generateToken(user.getUser());
    }
}
