package org.rifasya.main.repositories.listRepositories;

import org.rifasya.main.entities.listEntities.lists.ListDrawMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListDrawMethodRepository extends JpaRepository<ListDrawMethod, UUID> {

    Optional<ListDrawMethod> findByCode(String code);

    @Override
    <S extends ListDrawMethod> S save(S entity);

    @Override
    Optional<ListDrawMethod> findById(UUID uuid);

    @Override
    List<ListDrawMethod> findAll();

    @Override
    boolean existsById(UUID uuid);

    @Override
    void deleteById(UUID uuid);
}
