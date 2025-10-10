package org.rifasya.main.repositories.location;

import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.location.ThirdPartyLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ThirdPartyLocationRepository extends JpaRepository<ThirdPartyLocation, UUID> {

    Optional<ThirdPartyLocation> findByThirdPartyAndIndicatorCurrentTrue(ThirdParty thirdParty);

    List<ThirdPartyLocation> findByThirdParty(ThirdParty thirdParty);

    @Modifying
    @Query("UPDATE ThirdPartyLocation tpl SET tpl.indicatorCurrent = false WHERE tpl.thirdParty.id = :thirdPartyId")
    void deactivateAllCurrentLocationsForThirdParty(@Param("thirdPartyId") UUID thirdPartyId);
}
