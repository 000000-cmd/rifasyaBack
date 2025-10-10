package org.rifasya.main.repositories.location;

import org.rifasya.main.dto.response.location.projection.LocationSearchDTO;
import org.rifasya.main.entities.location.Country;
import org.rifasya.main.entities.location.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findByCode(String code);
    boolean existsByCode(String code);
    long countByCountryId(UUID countryId);
    List<Department> findByNameStartingWithIgnoreCase(String name);
    List<Department> findByCountry(Country country);

    @Query("SELECT new org.rifasya.main.dto.response.location.projection.LocationSearchDTO(d.id, d.code, d.name) " +
            "FROM Department d WHERE UPPER(d.name) LIKE UPPER(CONCAT(:name, '%')) ORDER BY d.name")
    List<LocationSearchDTO> findAsProjectionByNameStartingWith(@Param("name") String name);

    @Query("SELECT new org.rifasya.main.dto.response.location.projection.LocationSearchDTO(d.id, d.code, d.name) FROM Department d ORDER BY d.name")
    List<LocationSearchDTO> findAllAsProjection();

    @Query("SELECT new org.rifasya.main.dto.response.location.projection.LocationSearchDTO(d.id, d.code, d.name) FROM Department d WHERE d.country.id = :countryId ORDER BY d.name")
    List<LocationSearchDTO> findByCountryIdAsProjection(@Param("countryId") UUID countryId);
}
