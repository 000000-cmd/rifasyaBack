package org.rifasya.main.services;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.rifasya.main.dto.request.UserDTO.EmbeddedUserRequestDTO;
import org.rifasya.main.dto.response.UserDTO.UserResponseDTO;
import org.rifasya.main.entities.User;
import org.rifasya.main.entities.UserRole;
import org.rifasya.main.entities.listEntities.ListRole;
import org.rifasya.main.mappers.UserMapper;
import org.rifasya.main.models.UserModel;
import org.rifasya.main.repositories.UserRepository;
import org.rifasya.main.repositories.listRepositories.ListRoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserMapper userMapper;
    private final ListRoleRepository roleTypeRepo;

    public UserService(UserRepository userRepo, UserMapper userMapper, ListRoleRepository roleTypeRepo) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.roleTypeRepo = roleTypeRepo;
    }

    @Transactional
    public UserResponseDTO createUser(EmbeddedUserRequestDTO userRequestDTO) {
        // ... Pasos 1 y 2 (DTO -> Model -> Entity)
        UserModel userModel = userMapper.requestToModel(userRequestDTO);
        userModel.setId(UUID.randomUUID());
        User userEntity = userMapper.modelToEntity(userModel);

        // Asignar roles
        if (userRequestDTO.getRoleCodes() != null && !userRequestDTO.getRoleCodes().isEmpty()) {
            Set<UserRole> roles = new HashSet<>();
            for (String roleCode : userRequestDTO.getRoleCodes()) {
                ListRole roleType = roleTypeRepo.findByCode(roleCode)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleCode));
                roles.add(new UserRole(userEntity, roleType));
            }
            userEntity.setRoles(roles);
        }

        // ... Pasos 3, 4 y 5 (Password, Auditor, Guardar)
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        // ... (Tu lógica de auditoría existente)
        userEntity.setUserAudit(userEntity);
        userEntity = userRepo.save(userEntity);

        // ... Paso 6 (Retornar DTO)
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
