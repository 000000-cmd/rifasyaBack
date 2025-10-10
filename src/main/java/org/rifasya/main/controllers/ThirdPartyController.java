package org.rifasya.main.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.rifasya.main.dto.request.ThirdPartyRequestDTO;
import org.rifasya.main.dto.response.ThirdPartyResponseDTO;
import org.rifasya.main.dto.response.UserDTO.UserResponseDTO;
import org.rifasya.main.services.ThirdPartyService;
import org.rifasya.main.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/thirdparty")
public class ThirdPartyController {

    private final ThirdPartyService thirdPartyService;

    public ThirdPartyController(ThirdPartyService thirdPartyService) {
        this.thirdPartyService = thirdPartyService;
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<ThirdPartyResponseDTO> create(@Valid @RequestBody ThirdPartyRequestDTO dto) {
        ThirdPartyResponseDTO responseDTO = thirdPartyService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}

