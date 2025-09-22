package org.rifasya.main.services;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.rifasya.main.dto.request.UserRequestDTO;
import org.rifasya.main.dto.response.UserResponseDTO;
import org.rifasya.main.entities.User;
import org.rifasya.main.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public UserResponseDTO createUser (UserRequestDTO userRequestDTO) {
        User user = userRepo.save(Objects.requireNonNull(UserDTOToUserEntity(userRequestDTO)));
        if (user == null) { return null; }

        return UserEntityToResponseDTO(user);
    }

    // Conversión de UserRequestDTO a User
    private User UserDTOToUserEntity(UserRequestDTO userReqDTO) {
        User user = new User(UUID.randomUUID(),
                userReqDTO.getUser(),
                passwordEncoder.encode(userReqDTO.getPassword()),
                userReqDTO.getCellular(),
                userReqDTO.getMail(),
                true,
                null,
                LocalDateTime.now(),
                null);

        // Cargar usuario auditor desde el repositorio si existe
        if (userReqDTO.getUserAuditId() != null) {
            User auditor = userRepo.findById(userReqDTO.getUserAuditId())
                    .orElseThrow(() -> new RuntimeException("Usuario auditor no encontrado"));
            user.setUserAudit(auditor);
        } else {
            // Si no se pasa auditor, se asigna a sí mismo
            user.setUserAudit(user);
        }
        return user;
    }

    private UserResponseDTO UserEntityToResponseDTO(User user) {
        return new UserResponseDTO(user.getId(),
                user.getUser(),
                user.getCellular(),
                user.getMail(),
                user.getIndicatorEnabled(),
                LocalDateTime.now(),
                user.getAttachment()
        );
    }
}