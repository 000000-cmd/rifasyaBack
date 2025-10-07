package org.rifasya.main.services.listService;

import org.rifasya.main.dto.request.listDTO.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.entities.listEntities.ListCategory;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.repositories.listRepositories.ListCategoryRepository;
import org.rifasya.main.services.AbstractListService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
public class ListCategoryService extends AbstractListService<ListCategory, ListCategory, ListCategoryRepository> {

    public ListCategoryService(ListCategoryRepository repository, ListMapper mapper) {
        super(repository, mapper, "Categoría");
    }

    @Override
    protected Function<ListTypeRequestDTO, ListCategory> toEntity() { return mapper::toCategoryEntity; }

    @Override
    protected Function<ListCategory, ListTypeResponseDTO> toResponseDTO() { return mapper::toResponseDTO; }

    @Override
    protected BiConsumer<ListTypeRequestDTO, ListCategory> updateFromDto() { return mapper::updateCategoryFromDto; }

    @Override
    protected void setSharedProperties(ListCategory entity) {
        entity.setId(UUID.randomUUID());
        entity.setAuditDate(LocalDateTime.now());
    }

    @Override
    protected void setAuditDate(ListCategory entity, LocalDateTime date) {
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
