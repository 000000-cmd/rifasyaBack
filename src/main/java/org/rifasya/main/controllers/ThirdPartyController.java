package org.rifasya.main.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.rifasya.main.dto.request.ThirdPartyWithUserRequestDTO;
import org.rifasya.main.dto.response.ThirdPartyResponseDTO;
import org.rifasya.main.dto.response.ThirdPartyWhitUserResponseDTO;
import org.rifasya.main.dto.response.UserResponseDTO;
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

    private final UserService userService;
    private final ThirdPartyService thirdPartyService;

    public ThirdPartyController(UserService userService, ThirdPartyService thirdPartyService) {
        this.userService = userService;
        this.thirdPartyService = thirdPartyService;
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<ThirdPartyWhitUserResponseDTO> create(@Valid @RequestBody ThirdPartyWithUserRequestDTO dto) {
        ThirdPartyWhitUserResponseDTO ResponseDTO = new ThirdPartyWhitUserResponseDTO();

        UserResponseDTO userThirdParty = userService.createUser(dto.getUser());
        dto.getThirdParty().setIdUser(userThirdParty.getId());
        dto.getThirdParty().setUserAuditId(userThirdParty.getId());
        ThirdPartyResponseDTO ThirdParty= thirdPartyService.create(dto.getThirdParty());

        ResponseDTO.setThirdParty(ThirdParty);
        ResponseDTO.setUser(userThirdParty);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO);
    }
}
