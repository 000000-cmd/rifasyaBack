package org.rifasya.main.repositories.listRepositories;

import org.rifasya.main.entities.listEntities.lists.ListDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ListDocumentTypeRepository extends JpaRepository<ListDocumentType, UUID> {

    @Override
    <S extends ListDocumentType> S save(S entity);

    @Override
    Optional<ListDocumentType> findById(UUID uuid);

    Optional<ListDocumentType> findByCode(String code);

    @Override
    boolean existsById(UUID uuid);

    @Override
    long count();

    @Override
    void deleteById(UUID uuid);

    @Override
    void delete(ListDocumentType entity);

    @Override
    void deleteAllById(Iterable<? extends UUID> uuids);

    @Override
    void deleteAll(Iterable<? extends ListDocumentType> entities);

    @Override
    void deleteAll();
}