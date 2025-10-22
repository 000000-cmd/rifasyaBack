package org.rifasya.main.services.listService;

import org.rifasya.main.dto.request.list.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.entities.listEntities.lists.ListRaffleModel;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.repositories.listRepositories.ListRaffleModelRepository;
import org.rifasya.main.services.AbstractListService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
public class ListRaffleModelService extends AbstractListService<ListRaffleModel, ListRaffleModel, ListRaffleModelRepository> {

    public ListRaffleModelService(ListRaffleModelRepository repository, ListMapper mapper) {
        super(repository, mapper, "Modelo de Rifa");
    }

    @Override
    protected Function<ListTypeRequestDTO, ListRaffleModel> toEntity() { return mapper::toRaffleModelEntity; }

    @Override
    protected Function<ListRaffleModel, ListTypeResponseDTO> toResponseDTO() { return mapper::toResponseDTO; }

    @Override
    protected BiConsumer<ListTypeRequestDTO, ListRaffleModel> updateFromDto() { return mapper::updateRaffleModelFromDto; }

    @Override
    protected void setSharedProperties(ListRaffleModel entity) {
        entity.setId(UUID.randomUUID());
        entity.setAuditDate(LocalDateTime.now());
    }

    @Override
    protected void setAuditDate(ListRaffleModel entity, LocalDateTime date) {
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
