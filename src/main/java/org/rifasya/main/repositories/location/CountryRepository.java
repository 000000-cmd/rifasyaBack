package org.rifasya.main.repositories.location;

import org.rifasya.main.dto.response.location.projection.LocationSearchDTO;
import org.rifasya.main.entities.location.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Optional<Country> findByCode(String code);
    boolean existsByCode(String code);
    @Query("SELECT c FROM Country c LEFT JOIN FETCH c.departments d LEFT JOIN FETCH d.municipalities m LEFT JOIN FETCH m.neighborhoods n")
    List<Country> findAllWithNestedDetails();

    List<Country> findByNameStartingWithIgnoreCase(String name);

    @Query("SELECT new org.rifasya.main.dto.response.location.projection.LocationSearchDTO(c.id, c.code, c.name) " +
            "FROM Country c WHERE UPPER(c.name) LIKE UPPER(CONCAT(:name, '%')) ORDER BY c.name")
    List<LocationSearchDTO> findAsProjectionByNameStartingWith(@Param("name") String name);

    @Query("SELECT new org.rifasya.main.dto.response.location.projection.LocationSearchDTO(c.id, c.code, c.name) FROM Country c ORDER BY c.name")
    List<LocationSearchDTO> findAllAsProjection();
}
