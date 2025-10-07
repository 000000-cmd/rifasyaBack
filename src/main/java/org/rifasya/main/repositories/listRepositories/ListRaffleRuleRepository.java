package org.rifasya.main.repositories.listRepositories;

import org.rifasya.main.entities.listEntities.ListRaffleRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListRaffleRuleRepository extends JpaRepository<ListRaffleRule, UUID> {

    Optional<ListRaffleRule> findByCode(String code);

    @Override
    <S extends ListRaffleRule> S save(S entity);

    @Override
    Optional<ListRaffleRule> findById(UUID uuid);

    @Override
    List<ListRaffleRule> findAll();

    @Override
    boolean existsById(UUID uuid);

    @Override
    void deleteById(UUID uuid);
}
