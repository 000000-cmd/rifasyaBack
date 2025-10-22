package org.rifasya.main.services.listService;

import org.rifasya.main.dto.request.list.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.entities.listEntities.lists.ListRole;
import org.rifasya.main.mappers.ListMapper;
import org.rifasya.main.repositories.listRepositories.ListRoleRepository;
import org.rifasya.main.services.AbstractListService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
public class ListRoleService extends AbstractListService<ListRole, ListRole, ListRoleRepository> {

    public ListRoleService(ListRoleRepository repository, ListMapper mapper) {
        super(repository, mapper, "Tipo de Rol");
    }

    @Override
    protected Function<ListTypeRequestDTO, ListRole> toEntity() { return mapper::toRoleTypeEntity; }

    @Override
    protected Function<ListRole, ListTypeResponseDTO> toResponseDTO() { return mapper::toResponseDTO; }

    @Override
    protected BiConsumer<ListTypeRequestDTO, ListRole> updateFromDto() { return mapper::updateRoleTypeFromDto; }

    @Override
    protected void setSharedProperties(ListRole entity) {
        entity.setId(UUID.randomUUID());
        entity.setAuditDate(LocalDateTime.now());
    }

    @Override
    protected void setAuditDate(ListRole entity, LocalDateTime date) {
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
