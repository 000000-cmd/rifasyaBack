package org.rifasya.main.services;


import lombok.RequiredArgsConstructor;
import org.rifasya.main.dto.request.UserRequestDTO;
import org.rifasya.main.dto.response.UserResponseDTO;
import org.rifasya.main.entities.User;
import org.rifasya.main.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserResponseDTO createUser (UserRequestDTO userRequestDTO) {
        User user = userRepo.save(Objects.requireNonNull(UserDTOToUserRepo(userRequestDTO)));
        if (user == null) { return null; }

        return UserRepoToResponseDTO(user);
    }

    // Conversión de UserRequestDTO a User
    private User UserDTOToUserRepo(UserRequestDTO userReqDTO) {
        User user = new User(UUID.randomUUID(),
                userReqDTO.getUser(),
                userReqDTO.getPassword(),
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



    private UserResponseDTO UserRepoToResponseDTO (User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUser(user.getUser());
        userResponseDTO.setCellular(user.getCellular());
        userResponseDTO.setMail(user.getMail());
        userResponseDTO.setIndicatorEnabled(user.getIndicatorEnabled());

        return userResponseDTO;
    }

}