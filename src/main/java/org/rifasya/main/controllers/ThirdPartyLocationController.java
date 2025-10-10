package org.rifasya.main.controllers;

import jakarta.validation.Valid;
import org.rifasya.main.dto.request.locationDTO.LocationRequestDTO;
import org.rifasya.main.entities.User;
import org.rifasya.main.exceptions.ResourceNotFoundException;
import org.rifasya.main.repositories.UserRepository;
import org.rifasya.main.services.ThirdPartyLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/thirdparties")
public class ThirdPartyLocationController {

    private final ThirdPartyLocationService locationService;
    private final UserRepository userRepository; // 1. Inyectar UserRepository

    public ThirdPartyLocationController(ThirdPartyLocationService locationService, UserRepository userRepository) {
        this.locationService = locationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/{thirdPartyId}/locations")
    public ResponseEntity<Void> addLocation(
            @PathVariable UUID thirdPartyId,
            @Valid @RequestBody LocationRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) { // 2. Obtener UserDetails del usuario autenticado

        // 3. Buscar el usuario completo en la base de datos
        User currentUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("El usuario auditor no fue encontrado."));

        // 4. Pasar el usuario persistido al servicio
        locationService.addOrUpdateLocation(thirdPartyId, dto, currentUser);

        return ResponseEntity.ok().build();
    }
}
