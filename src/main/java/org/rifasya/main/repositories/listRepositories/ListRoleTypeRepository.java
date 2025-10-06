package org.rifasya.main.repositories.listRepositories;

import org.rifasya.main.entities.listEntities.ListRoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ListRoleTypeRepository extends JpaRepository<ListRoleType, UUID> {
    Optional<ListRoleType> findByCode(String code);
}
