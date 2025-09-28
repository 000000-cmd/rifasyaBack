package org.rifasya.main.services;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.rifasya.main.dto.request.UserDTO.EmbeddedUserRequestDTO;
import org.rifasya.main.dto.response.UserDTO.UserResponseDTO;
import org.rifasya.main.entities.User;
import org.rifasya.main.mappers.UserMapper;
import org.rifasya.main.models.UserModel;
import org.rifasya.main.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserMapper userMapper;

    public UserService(UserRepository userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserResponseDTO createUser(EmbeddedUserRequestDTO userRequestDTO) {

        // 1️⃣ DTO -> Model
        UserModel userModel = userMapper.requestToModel(userRequestDTO);
        userModel.setId(UUID.randomUUID());

        // 2️⃣ Model -> Entity
        User userEntity = userMapper.modelToEntity(userModel);

        // 3️⃣ Codificar password
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        // 4️⃣ Asignar auditor
        if (userModel.getId() != null) {
            User auditor = userRepo.findById(userModel.getId())
                    .orElse(userEntity); // si no existe, se asigna a sí mismo
            userEntity.setUserAudit(auditor);
        } else {
            userEntity.setUserAudit(userEntity);
        }

        // 5️⃣ Guardar en BD
        userEntity = userRepo.save(userEntity);

        // 6️⃣ Entity -> Model -> ResponseDTO
        userModel = userMapper.entityToModel(userEntity);
        return userMapper.modelToResponseDTO(userModel);
    }

    // Método auxiliar para persistir UserModel directamente
    @Transactional
    public User saveUser(UserModel userModel) {
        User userEntity = userMapper.modelToEntity(userModel);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity = userRepo.save(userEntity);
        return userEntity;
    }
}
