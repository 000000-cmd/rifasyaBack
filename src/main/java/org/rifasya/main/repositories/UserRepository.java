package org.rifasya.main.repositories;

import org.rifasya.main.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Override
    <S extends User> S save(S entity);

    @Override
    Optional<User> findById(UUID uuid);

    @Override
    boolean existsById(UUID uuid);

    @Override
    long count();

    @Override
    void deleteById(UUID uuid);

    @Override
    void delete(User entity);

    @Override
    void deleteAllById(Iterable<? extends UUID> uuids);

    @Override
    void deleteAll(Iterable<? extends User> entities);

    @Override
    void deleteAll();
}
