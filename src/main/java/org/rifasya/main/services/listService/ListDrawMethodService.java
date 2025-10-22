package org.rifasya.main.services.listService;

import org.rifasya.main.dto.request.list.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.entities.listEntities.lists.ListDrawMethod;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.repositories.listRepositories.ListDrawMethodRepository;
import org.rifasya.main.services.AbstractListService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
public class ListDrawMethodService extends AbstractListService<ListDrawMethod, ListDrawMethod, ListDrawMethodRepository> {

    public ListDrawMethodService(ListDrawMethodRepository repository, ListMapper mapper) {
        super(repository, mapper, "Método de Sorteo");
    }

    @Override
    protected Function<ListTypeRequestDTO, ListDrawMethod> toEntity() { return mapper::toDrawMethodEntity; }

    @Override
    protected Function<ListDrawMethod, ListTypeResponseDTO> toResponseDTO() { return mapper::toResponseDTO; }

    @Override
    protected BiConsumer<ListTypeRequestDTO, ListDrawMethod> updateFromDto() { return mapper::updateDrawMethodFromDto; }

    @Override
    protected void setSharedProperties(ListDrawMethod entity) {
        entity.setId(UUID.randomUUID());
        entity.setAuditDate(LocalDateTime.now());
    }

    @Override
    protected void setAuditDate(ListDrawMethod entity, LocalDateTime date) {
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
