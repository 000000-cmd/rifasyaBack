package org.rifasya.main.services.listService;

import org.rifasya.main.dto.request.list.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.entities.listEntities.lists.ListExternalLottery;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.repositories.listRepositories.ListExternalLotteryRepository;
import org.rifasya.main.services.AbstractListService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
public class ListExternalLotteryService extends AbstractListService<ListExternalLottery, ListExternalLottery, ListExternalLotteryRepository> {

    public ListExternalLotteryService(ListExternalLotteryRepository repository, ListMapper mapper) {
        super(repository, mapper, "Lotería Externa");
    }

    @Override
    protected Function<ListTypeRequestDTO, ListExternalLottery> toEntity() { return mapper::toExternalLotteryEntity; }

    @Override
    protected Function<ListExternalLottery, ListTypeResponseDTO> toResponseDTO() { return mapper::toResponseDTO; }

    @Override
    protected BiConsumer<ListTypeRequestDTO, ListExternalLottery> updateFromDto() { return mapper::updateExternalLotteryFromDto; }

    @Override
    protected void setSharedProperties(ListExternalLottery entity) {
        entity.setId(UUID.randomUUID());
        entity.setAuditDate(LocalDateTime.now());
    }

    @Override
    protected void setAuditDate(ListExternalLottery entity, LocalDateTime date) {
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