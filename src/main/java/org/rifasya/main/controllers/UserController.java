package org.rifasya.main.controllers;

import jakarta.validation.Valid;
import org.rifasya.main.dto.request.User.EmbeddedUserRequestDTO;
import org.rifasya.main.dto.response.User.UserResponseDTO;
import org.rifasya.main.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody EmbeddedUserRequestDTO userRequestDTO) {
        // Permite crear un usuario de forma independiente
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}

