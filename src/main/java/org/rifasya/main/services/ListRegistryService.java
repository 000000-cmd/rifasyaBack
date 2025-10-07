package org.rifasya.main.services;

import org.rifasya.main.dto.request.listDTO.ListRegistryRequestDTO;
import org.rifasya.main.dto.response.ListRegistryResponseDTO;
import org.rifasya.main.entities.meta.ListRegistry;
import org.rifasya.main.exceptions.ResourceNotFoundException;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.repositories.meta.ListRegistryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListRegistryService {

    private final ListRegistryRepository repository;
    private final ListMapper mapper;

    public ListRegistryService(ListRegistryRepository repository, ListMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<ListRegistryResponseDTO> findAll() {
        return repository.findAll().stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ListRegistryResponseDTO findById(UUID id) {
        ListRegistry entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de lista no encontrado con ID: " + id));
        return mapper.toResponseDTO(entity);
    }

    @Transactional
    public ListRegistryResponseDTO create(ListRegistryRequestDTO dto) {
        repository.findByTechnicalName(dto.getTechnicalName()).ifPresent(e -> {
            throw new IllegalArgumentException("El nombre técnico '" + dto.getTechnicalName() + "' ya existe.");
        });

        ListRegistry newEntity = mapper.toEntity(dto);
        newEntity.setId(UUID.randomUUID());

        return mapper.toResponseDTO(repository.save(newEntity));
    }

    @Transactional
    public ListRegistryResponseDTO update(UUID id, ListRegistryRequestDTO dto) {
        ListRegistry existingEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de lista no encontrado con ID: " + id));

        mapper.updateFromDto(dto, existingEntity);
        return mapper.toResponseDTO(repository.save(existingEntity));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Registro de lista no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }
}