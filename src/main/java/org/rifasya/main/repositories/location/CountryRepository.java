package org.rifasya.main.repositories.location;

import org.rifasya.main.entities.location.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Optional<Country> findByCode(String code);
}
