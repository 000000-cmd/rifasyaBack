package org.rifasya.main.repositories.location;

import org.rifasya.main.dto.response.location.projection.LocationSearchDTO;
import org.rifasya.main.entities.location.Municipality;
import org.rifasya.main.entities.location.Neighborhood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NeighborhoodRepository extends JpaRepository<Neighborhood, UUID> {
    Optional<Neighborhood> findByCode(String code);
    boolean existsByCode(String code);
    long countByMunicipalityId(UUID municipalityId);
    List<Neighborhood> findByNameStartingWithIgnoreCase(String name);
    List<Neighborhood> findByMunicipality(Municipality municipality);

    @Query("SELECT new org.rifasya.main.dto.response.location.projection.LocationSearchDTO(n.id, n.code, n.name) " +
            "FROM Neighborhood n WHERE UPPER(n.name) LIKE UPPER(CONCAT(:name, '%')) ORDER BY n.name")
    List<LocationSearchDTO> findAsProjectionByNameStartingWith(@Param("name") String name);

    @Query("SELECT new org.rifasya.main.dto.response.location.projection.LocationSearchDTO(n.id, n.code, n.name) FROM Neighborhood n ORDER BY n.name")
    List<LocationSearchDTO> findAllAsProjection();

    @Query("SELECT new org.rifasya.main.dto.response.location.projection.LocationSearchDTO(n.id, n.code, n.name) FROM Neighborhood n WHERE n.municipality.id = :municipalityId ORDER BY n.name")
    List<LocationSearchDTO> findByMunicipalityIdAsProjection(@Param("municipalityId") UUID municipalityId);
}
