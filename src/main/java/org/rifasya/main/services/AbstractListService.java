package org.rifasya.main.services;

import org.rifasya.main.dto.request.list.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.entities.listEntities.ListItemBase;
import org.rifasya.main.exceptions.ResourceNotFoundException;
import org.rifasya.main.mappers.ListMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractListService<E extends ListItemBase, T extends E, R extends JpaRepository<E, UUID>> {

    protected final R repository;
    protected final ListMapper mapper;
    protected final String entityName;

    public AbstractListService(R repository, ListMapper mapper, String entityName) {
        this.repository = repository;
        this.mapper = mapper;
        this.entityName = entityName;
    }

    protected abstract Function<ListTypeRequestDTO, T> toEntity();
    protected abstract Function<E, ListTypeResponseDTO> toResponseDTO();
    protected abstract BiConsumer<ListTypeRequestDTO, T> updateFromDto();
    protected abstract void setSharedProperties(T entity);
    protected abstract void setAuditDate(T entity, LocalDateTime date);

    @Transactional(readOnly = true)
    public List<ListTypeResponseDTO> findAll() {
        return repository.findAll().stream().map(toResponseDTO()).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ListTypeResponseDTO findById(UUID id) {
        E entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(entityName + " no encontrado con ID: " + id));
        return toResponseDTO().apply(entity);
    }

    @Transactional
    public ListTypeResponseDTO create(ListTypeRequestDTO requestDTO) {
        T newEntity = toEntity().apply(requestDTO);
        setSharedProperties(newEntity);
        E savedEntity = repository.save(newEntity);
        return toResponseDTO().apply(savedEntity);
    }

    @Transactional
    public ListTypeResponseDTO update(UUID id, ListTypeRequestDTO requestDTO) {
        T existingEntity = (T) repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName + " no encontrado para actualizar con ID: " + id));
        updateFromDto().accept(requestDTO, existingEntity);
        setAuditDate(existingEntity, LocalDateTime.now());
        E updatedEntity = repository.save(existingEntity);
        return toResponseDTO().apply(updatedEntity);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(entityName + " no encontrado para eliminar con ID: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public void updateOrder(List<UUID> orderedIds) {
        List<E> itemsToReorder = repository.findAllById(orderedIds);
        if (itemsToReorder.size() != orderedIds.size()) {
            throw new ResourceNotFoundException("Uno o más ítems no fueron encontrados durante el reordenamiento.");
        }
        var itemsMap = itemsToReorder.stream().collect(Collectors.toMap(ListItemBase::getId, item -> item));
        for (int i = 0; i < orderedIds.size(); i++) {
            E item = itemsMap.get(orderedIds.get(i));
            if (item != null) {
                item.setOrder(i + 1);
                repository.save(item);
            }
        }
    }
}