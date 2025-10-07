package org.rifasya.main.services.listService;

import org.rifasya.main.dto.request.listDTO.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.entities.listEntities.ListPrizeType;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.repositories.listRepositories.ListPrizeTypeRepository;
import org.rifasya.main.services.AbstractListService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
public class ListPrizeTypeService extends AbstractListService<ListPrizeType, ListPrizeType, ListPrizeTypeRepository> {

    public ListPrizeTypeService(ListPrizeTypeRepository repository, ListMapper mapper) {
        super(repository, mapper, "Tipo de Premio");
    }

    @Override
    protected Function<ListTypeRequestDTO, ListPrizeType> toEntity() { return mapper::toPrizeTypeEntity; }

    @Override
    protected Function<ListPrizeType, ListTypeResponseDTO> toResponseDTO() { return mapper::toResponseDTO; }

    @Override
    protected BiConsumer<ListTypeRequestDTO, ListPrizeType> updateFromDto() { return mapper::updatePrizeTypeFromDto; }

    @Override
    protected void setSharedProperties(ListPrizeType entity) {
        entity.setId(UUID.randomUUID());
        entity.setAuditDate(LocalDateTime.now());
    }

    @Override
    protected void setAuditDate(ListPrizeType entity, LocalDateTime date) {
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
