package org.rifasya.main.services;

import org.rifasya.main.dto.request.ConstantsRequestDTO;
import org.rifasya.main.dto.response.ConstantsResponseDTO;
import org.rifasya.main.entities.Constants;
import org.rifasya.main.entities.User;
import org.rifasya.main.exceptions.DuplicateResourceException;
import org.rifasya.main.exceptions.ResourceNotFoundException;
import org.rifasya.main.mappers.ConstantsMapper;
import org.rifasya.main.repositories.ConstantsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- Importación corregida

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConstantsService {

    private final ConstantsRepository constantsRepository;
    private final ConstantsMapper constantsMapper;
    private final UserService userService; // Inyectado para la auditoría

    public ConstantsService(ConstantsRepository constantsRepository, ConstantsMapper constantsMapper, UserService userService) {
        this.constantsRepository = constantsRepository;
        this.constantsMapper = constantsMapper;
        this.userService = userService;
    }

    /**
     * Obtiene todas las constantes.
     * @return Una lista de constantes en formato DTO.
     */
    @Transactional(readOnly = true)
    public List<ConstantsResponseDTO> getAllConstants() {
        return constantsRepository.findAll().stream()
                .map(constantsMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una constante por su ID.
     * @param id El UUID de la constante.
     * @return La constante encontrada en formato DTO.
     * @throws ResourceNotFoundException si no se encuentra la constante.
     */
    @Transactional(readOnly = true)
    public ConstantsResponseDTO getConstantById(UUID id) {
        Constants constant = constantsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Constante no encontrada con ID: " + id));
        return constantsMapper.toResponseDTO(constant);
    }

    /**
     * Crea una nueva constante.
     * @param dto Los datos para la nueva constante.
     * @return La constante creada en formato DTO.
     * @throws DuplicateResourceException si el código ya existe.
     */
    @Transactional
    public ConstantsResponseDTO createConstant(ConstantsRequestDTO dto) {
        constantsRepository.findByCode(dto.code()).ifPresent(c -> {
            throw new DuplicateResourceException("Ya existe una constante con el código: " + dto.code());
        });

        Constants newConstant = constantsMapper.toEntity(dto);

        // Asigna el usuario y la fecha de auditoría automáticamente
        User currentUser = userService.getCurrentAuthenticatedUser();
        newConstant.setUserAudit(currentUser);
        newConstant.setAuditDate(LocalDateTime.now());

        Constants savedConstant = constantsRepository.save(newConstant);
        return constantsMapper.toResponseDTO(savedConstant);
    }

    /**
     * Actualiza una constante existente.
     * @param id El UUID de la constante a actualizar.
     * @param dto Los nuevos datos para la constante.
     * @return La constante actualizada en formato DTO.
     * @throws ResourceNotFoundException si no se encuentra la constante.
     * @throws DuplicateResourceException si el nuevo código ya está en uso por otra constante.
     */
    @Transactional
    public ConstantsResponseDTO updateConstant(UUID id, ConstantsRequestDTO dto) {
        Constants existingConstant = constantsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Constante no encontrada con ID: " + id));

        constantsRepository.findByCode(dto.code()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new DuplicateResourceException("El código '" + dto.code() + "' ya está en uso por otra constante.");
            }
        });

        constantsMapper.updateEntityFromDto(dto, existingConstant);

        // Actualiza la auditoría
        User currentUser = userService.getCurrentAuthenticatedUser();
        existingConstant.setUserAudit(currentUser);
        existingConstant.setAuditDate(LocalDateTime.now());

        Constants updatedConstant = constantsRepository.save(existingConstant);
        return constantsMapper.toResponseDTO(updatedConstant);
    }

    /**
     * Elimina una constante por su ID.
     * @param id El UUID de la constante a eliminar.
     * @throws ResourceNotFoundException si no se encuentra la constante.
     */
    @Transactional
    public void deleteConstant(UUID id) {
        if (!constantsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Constante no encontrada con ID: " + id);
        }
        constantsRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public ConstantsResponseDTO getConstantByCode(String code) {
        Constants constant = constantsRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Constante no encontrada con Código: " + code));
        return constantsMapper.toResponseDTO(constant);
    }
}