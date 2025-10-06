package org.rifasya.main.repositories;

import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ThirdPartyRepository extends JpaRepository<ThirdParty, UUID> {
    @Override
    <S extends ThirdParty> S save(S entity);

    @Override
    Optional<ThirdParty> findById(UUID uuid);

    @Override
    boolean existsById(UUID uuid);

    @Override
    long count();

    @Override
    void deleteById(UUID uuid);

    @Override
    void delete(ThirdParty entity);

    @Override
    void deleteAllById(Iterable<? extends UUID> uuids);

    @Override
    void deleteAll(Iterable<? extends ThirdParty> entities);

    @Override
    void deleteAll();

    Optional<ThirdParty> findByUser(User user);
}
