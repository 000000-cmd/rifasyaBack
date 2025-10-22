package org.rifasya.main.controllers;

import jakarta.validation.Valid;
import org.rifasya.main.dto.request.ConstantsRequestDTO;
import org.rifasya.main.dto.response.ConstantsResponseDTO;
import org.rifasya.main.services.ConstantsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/constants")
public class ConstantsController {

    private final ConstantsService constantsService;

    public ConstantsController(ConstantsService constantsService) {
        this.constantsService = constantsService;
    }

    @GetMapping
    public ResponseEntity<List<ConstantsResponseDTO>> getAllConstants() {
        return ResponseEntity.ok(constantsService.getAllConstants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConstantsResponseDTO> getConstantById(@PathVariable UUID id) {
        return ResponseEntity.ok(constantsService.getConstantById(id));
    }

    @PostMapping
    public ResponseEntity<ConstantsResponseDTO> createConstant(@Valid @RequestBody ConstantsRequestDTO dto) {
        ConstantsResponseDTO createdConstant = constantsService.createConstant(dto);
        return new ResponseEntity<>(createdConstant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConstantsResponseDTO> updateConstant(@PathVariable UUID id, @Valid @RequestBody ConstantsRequestDTO dto) {
        return ResponseEntity.ok(constantsService.updateConstant(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConstant(@PathVariable UUID id) {
        constantsService.deleteConstant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byCode/{code}")
    public ResponseEntity<ConstantsResponseDTO> getConstantByCode(@PathVariable String code) {
        return ResponseEntity.ok(constantsService.getConstantByCode(code));
    }
}