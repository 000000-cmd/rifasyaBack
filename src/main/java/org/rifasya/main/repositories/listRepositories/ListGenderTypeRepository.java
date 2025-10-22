package org.rifasya.main.repositories.listRepositories;


import org.rifasya.main.entities.listEntities.lists.ListGenderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ListGenderTypeRepository extends JpaRepository<ListGenderType, UUID> {

    @Override
    <S extends ListGenderType> S save(S entity);

    @Override
    Optional<ListGenderType> findById(UUID uuid);

    Optional<ListGenderType> findByCode(String Code);

    @Override
    boolean existsById(UUID uuid);

    @Override
    long count();

    @Override
    void deleteById(UUID uuid);

    @Override
    void delete(ListGenderType entity);

    @Override
    void deleteAllById(Iterable<? extends UUID> uuids);

    @Override
    void deleteAll(Iterable<? extends ListGenderType> entities);

    @Override
    void deleteAll();
}
