package org.rifasya.main.repositories.listRepositories;

import org.rifasya.main.entities.listEntities.lists.ListPrizeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListPrizeTypeRepository extends JpaRepository<ListPrizeType, UUID> {

    Optional<ListPrizeType> findByCode(String code);

    @Override
    <S extends ListPrizeType> S save(S entity);

    @Override
    Optional<ListPrizeType> findById(UUID uuid);

    @Override
    List<ListPrizeType> findAll();

    @Override
    boolean existsById(UUID uuid);

    @Override
    void deleteById(UUID uuid);
}
