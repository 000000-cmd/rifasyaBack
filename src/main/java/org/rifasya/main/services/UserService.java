package org.rifasya.main.services;


import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.rifasya.main.dto.request.User.EmbeddedUserRequestDTO;
import org.rifasya.main.dto.response.User.UserResponseDTO;
import org.rifasya.main.entities.User;
import org.rifasya.main.entities.UserRole;
import org.rifasya.main.entities.listEntities.lists.ListRole;
import org.rifasya.main.exceptions.DuplicateResourceException;
import org.rifasya.main.exceptions.ResourceNotFoundException;
import org.rifasya.main.mappers.UserMapper;
import org.rifasya.main.models.UserModel;
import org.rifasya.main.repositories.UserRepository;
import org.rifasya.main.repositories.listRepositories.ListRoleRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final ListRoleRepository roleRepo;

    public UserService(UserRepository userRepo, UserMapper userMapper, ListRoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.roleRepo = roleRepo;
    }

    @Transactional
    public UserResponseDTO createUser(EmbeddedUserRequestDTO userRequestDTO) {
        // Validar duplicados
        if (userRepo.existsByUsername(userRequestDTO.getUsername())) {
            throw new DuplicateResourceException("El nombre de usuario '" + userRequestDTO.getUsername() + "' ya está en uso.");
        }
        if (userRepo.existsByEmail(userRequestDTO.getEmail())) {
            throw new DuplicateResourceException("El correo electrónico '" + userRequestDTO.getEmail() + "' ya está registrado.");
        }

        UserModel userModel = userMapper.requestToModel(userRequestDTO);
        userModel.setId(UUID.randomUUID());
        User userEntity = userMapper.modelToEntity(userModel);

        if (userRequestDTO.getRoleCodes() != null && !userRequestDTO.getRoleCodes().isEmpty()) {
            Set<UserRole> roles = new HashSet<>();
            for (String roleCode : userRequestDTO.getRoleCodes()) {
                ListRole roleType = roleRepo.findByCode(roleCode)
                        .orElseThrow(() -> new ResourceNotFoundException("El rol con código '" + roleCode + "' no existe."));
                roles.add(new UserRole(userEntity, roleType));
            }
            userEntity.setRoles(roles);
        }

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setUserAudit(userEntity);
        userEntity = userRepo.save(userEntity);

        userModel = userMapper.entityToModel(userEntity);
        return userMapper.modelToResponseDTO(userModel);
    }

    @Transactional(readOnly = true)
    public User getCurrentAuthenticatedUser() {
        // 1. Obtenemos el objeto 'principal' del contexto de seguridad.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // 2. Usamos el username para buscar al usuario completo en la base de datos.
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario autenticado no encontrado en la base de datos: " + username));
    }
}
