package org.rifasya.main.services.listService;

import org.rifasya.main.dto.request.list.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.entities.listEntities.ListDocumentType;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.repositories.listRepositories.ListDocumentTypeRepository;
import org.rifasya.main.services.AbstractListService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
public class ListDocumentTypeService extends AbstractListService<ListDocumentType, ListDocumentType, ListDocumentTypeRepository> {

    public ListDocumentTypeService(ListDocumentTypeRepository repository, ListMapper mapper) {
        super(repository, mapper, "Tipo de Documento");
    }

    @Override
    protected Function<ListTypeRequestDTO, ListDocumentType> toEntity() { return mapper::toDocumentTypeEntity; }

    @Override
    protected Function<ListDocumentType, ListTypeResponseDTO> toResponseDTO() { return mapper::toResponseDTO; }

    @Override
    protected BiConsumer<ListTypeRequestDTO, ListDocumentType> updateFromDto() { return mapper::updateDocumentTypeFromDto; }

    @Override
    protected void setSharedProperties(ListDocumentType entity) {
        entity.setId(UUID.randomUUID());
        entity.setAuditDate(LocalDateTime.now());
    }

    @Override
    protected void setAuditDate(ListDocumentType entity, LocalDateTime date) {
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