package org.rifasya.main.services.listService;

import org.rifasya.main.dto.request.listDTO.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.entities.listEntities.ListGenderType;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.repositories.listRepositories.ListGenderTypeRepository;
import org.rifasya.main.services.AbstractListService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
public class ListGenderTypeService extends AbstractListService<ListGenderType, ListGenderType, ListGenderTypeRepository> {

    public ListGenderTypeService(ListGenderTypeRepository repository, ListMapper mapper) {
        super(repository, mapper, "Tipo de Género");
    }

    @Override
    protected Function<ListTypeRequestDTO, ListGenderType> toEntity() { return mapper::toGenderTypeEntity; }

    @Override
    protected Function<ListGenderType, ListTypeResponseDTO> toResponseDTO() { return mapper::toResponseDTO; }

    @Override
    protected BiConsumer<ListTypeRequestDTO, ListGenderType> updateFromDto() { return mapper::updateGenderTypeFromDto; }

    @Override
    protected void setSharedProperties(ListGenderType entity) {
        entity.setId(UUID.randomUUID());
        entity.setAuditDate(LocalDateTime.now());
    }

    @Override
    protected void setAuditDate(ListGenderType entity, LocalDateTime date) {
        entity.setAuditDate(date);
    }

    @Override
    public ListTypeResponseDTO create(ListTypeRequestDTO requestDTO) {
        repository.findByCode(requestDTO.getCode()).ifPresent(e -> {
            throw new IllegalArgumentException("El código '" + requestDTO.getCode() + "' ya existe para " + entityName);
        });
        return super.create(requestDTO);
    }
}