package org.rifasya.main.repositories.location;

import org.rifasya.main.dto.response.location.projection.LocationSearchDTO;
import org.rifasya.main.entities.location.Department;
import org.rifasya.main.entities.location.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MunicipalityRepository extends JpaRepository<Municipality, UUID> {
    Optional<Municipality> findByCode(String code);
    boolean existsByCode(String code);
    long countByDepartmentId(UUID departmentId);
    List<Municipality> findByNameStartingWithIgnoreCase(String name);
    List<Municipality> findByDepartment(Department department);

    @Query("SELECT new org.rifasya.main.dto.response.location.projection.LocationSearchDTO(m.id, m.code, m.name) " +
            "FROM Municipality m WHERE UPPER(m.name) LIKE UPPER(CONCAT(:name, '%')) ORDER BY m.name")
    List<LocationSearchDTO> findAsProjectionByNameStartingWith(@Param("name") String name);

    @Query("SELECT new org.rifasya.main.dto.response.location.projection.LocationSearchDTO(m.id, m.code, m.name) FROM Municipality m ORDER BY m.name")
    List<LocationSearchDTO> findAllAsProjection();

    @Query("SELECT new org.rifasya.main.dto.response.location.projection.LocationSearchDTO(m.id, m.code, m.name) FROM Municipality m WHERE m.department.id = :departmentId ORDER BY m.name")
    List<LocationSearchDTO> findByDepartmentIdAsProjection(@Param("departmentId") UUID departmentId);
}