package org.rifasya.main.services.listService;

import org.rifasya.main.dto.request.listDTO.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.entities.listEntities.ListRaffleRule;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.repositories.listRepositories.ListRaffleRuleRepository;
import org.rifasya.main.services.AbstractListService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
public class ListRaffleRuleService extends AbstractListService<ListRaffleRule, ListRaffleRule, ListRaffleRuleRepository> {

    public ListRaffleRuleService(ListRaffleRuleRepository repository, ListMapper mapper) {
        super(repository, mapper, "Regla de Rifa");
    }

    @Override
    protected Function<ListTypeRequestDTO, ListRaffleRule> toEntity() { return mapper::toRaffleRuleEntity; }

    @Override
    protected Function<ListRaffleRule, ListTypeResponseDTO> toResponseDTO() { return mapper::toResponseDTO; }

    @Override
    protected BiConsumer<ListTypeRequestDTO, ListRaffleRule> updateFromDto() { return mapper::updateRaffleRuleFromDto; }

    @Override
    protected void setSharedProperties(ListRaffleRule entity) {
        entity.setId(UUID.randomUUID());
        entity.setAuditDate(LocalDateTime.now());
    }

    @Override
    protected void setAuditDate(ListRaffleRule entity, LocalDateTime date) {
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