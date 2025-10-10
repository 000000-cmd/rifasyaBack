package org.rifasya.main.repositories.location;

import org.rifasya.main.entities.location.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface NeighborhoodRepository extends JpaRepository<Neighborhood, UUID> {
    Optional<Neighborhood> findByCode(String code);
}
