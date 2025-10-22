package org.rifasya.main.repositories;

import org.rifasya.main.entities.Constants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConstantsRepository extends JpaRepository<Constants, UUID> {

    /**
     * Busca una constante por su campo 'code'.
     * @param code El código a buscar.
     * @return un Optional que contiene la constante si se encuentra.
     */
    Optional<Constants> findByCode(String code);
}