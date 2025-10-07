package org.rifasya.main.repositories.meta;

import org.rifasya.main.entities.meta.ListRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListRegistryRepository extends JpaRepository<ListRegistry, UUID> {

    Optional<ListRegistry> findByTechnicalName(String technicalName);

    @Override
    <S extends ListRegistry> S save(S entity);

    @Override
    Optional<ListRegistry> findById(UUID uuid);

    @Override
    List<ListRegistry> findAll();

    @Override
    boolean existsById(UUID uuid);

    @Override
    void deleteById(UUID uuid);
}
