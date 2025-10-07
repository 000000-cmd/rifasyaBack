package org.rifasya.main.repositories.listRepositories;

import org.rifasya.main.entities.listEntities.ListExternalLottery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListExternalLotteryRepository extends JpaRepository<ListExternalLottery, UUID> {

    Optional<ListExternalLottery> findByCode(String code);

    @Override
    <S extends ListExternalLottery> S save(S entity);

    @Override
    Optional<ListExternalLottery> findById(UUID uuid);

    @Override
    List<ListExternalLottery> findAll();

    @Override
    boolean existsById(UUID uuid);

    @Override
    void deleteById(UUID uuid);
}
