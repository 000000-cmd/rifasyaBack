package org.rifasya.main.repositories.listRepositories;

import org.rifasya.main.entities.listEntities.lists.ListCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ListCategoryRepository extends JpaRepository<ListCategory, UUID> {

    Optional<ListCategory> findByCode(String code);

    @Override
    <S extends ListCategory> S save(S entity);

    @Override
    Optional<ListCategory> findById(UUID uuid);

    @Override
    boolean existsById(UUID uuid);

    @Override
    void deleteById(UUID uuid);
}
