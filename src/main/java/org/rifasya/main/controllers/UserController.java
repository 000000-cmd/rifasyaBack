package org.rifasya.main.controllers;

import jakarta.validation.Valid;
import org.rifasya.main.dto.request.User.EmbeddedUserRequestDTO;
import org.rifasya.main.dto.response.User.UserResponseDTO;
import org.rifasya.main.entities.User;
import org.rifasya.main.mappers.UserMapper;
import org.rifasya.main.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody EmbeddedUserRequestDTO userRequestDTO) {
        // Permite crear un usuario de forma independiente
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyProfile() {
        // 1. Llama al nuevo método del servicio.
        User currentUser = userService.getCurrentAuthenticatedUser();

        // 2. Mapea la entidad a un DTO de respuesta y lo devuelve.
        UserResponseDTO response = userMapper.modelToResponseDTO(userMapper.entityToModel(currentUser));

        return ResponseEntity.ok(response);
    }
}

