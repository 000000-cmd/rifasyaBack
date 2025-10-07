package org.rifasya.main.repositories.listRepositories;

import org.rifasya.main.entities.listEntities.ListRaffleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListRaffleModelRepository extends JpaRepository<ListRaffleModel, UUID> {

    Optional<ListRaffleModel> findByCode(String code);

    @Override
    <S extends ListRaffleModel> S save(S entity);

    @Override
    Optional<ListRaffleModel> findById(UUID uuid);

    @Override
    List<ListRaffleModel> findAll();

    @Override
    boolean existsById(UUID uuid);

    @Override
    void deleteById(UUID uuid);
}
