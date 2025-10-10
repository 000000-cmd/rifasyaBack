package org.rifasya.main.services;

import jakarta.transaction.Transactional;
import org.rifasya.main.dto.request.locationDTO.LocationRequestDTO;
import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import org.rifasya.main.entities.location.*;
import org.rifasya.main.exceptions.ResourceNotFoundException;
import org.rifasya.main.repositories.ThirdPartyRepository;
import org.rifasya.main.repositories.location.NeighborhoodRepository;
import org.rifasya.main.repositories.location.ThirdPartyLocationRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ThirdPartyLocationService {

    private final ThirdPartyRepository thirdPartyRepository;
    private final ThirdPartyLocationRepository locationRepository;
    private final NeighborhoodRepository neighborhoodRepository;

    public ThirdPartyLocationService(ThirdPartyRepository thirdPartyRepository,
                                     ThirdPartyLocationRepository locationRepository,
                                     NeighborhoodRepository neighborhoodRepository) {
        this.thirdPartyRepository = thirdPartyRepository;
        this.locationRepository = locationRepository;
        this.neighborhoodRepository = neighborhoodRepository;
    }

    @Transactional
    public void addOrUpdateLocation(UUID thirdPartyId, LocationRequestDTO locationDto, User userAudit) {
        ThirdParty thirdParty = thirdPartyRepository.findById(thirdPartyId)
                .orElseThrow(() -> new ResourceNotFoundException("El tercero con ID '" + thirdPartyId + "' no fue encontrado."));

        if (locationDto.getIsCurrent()) {
            locationRepository.deactivateAllCurrentLocationsForThirdParty(thirdPartyId);
        }

        Neighborhood neighborhood = neighborhoodRepository.findByCode(locationDto.getNeighborhoodCode())
                .orElseThrow(() -> new ResourceNotFoundException("El barrio/vereda con código '" + locationDto.getNeighborhoodCode() + "' no fue encontrado."));

        Municipality municipality = neighborhood.getMunicipality();
        Department department = municipality.getDepartment();
        Country country = department.getCountry();

        ThirdPartyLocation location = new ThirdPartyLocation();
        location.setThirdParty(thirdParty);
        location.setAddress(locationDto.getAddress());
        location.setAddressComplement(locationDto.getAddressComplement());
        location.setNeighborhood(neighborhood);
        location.setMunicipality(municipality);
        location.setDepartment(department);
        location.setCountry(country);
        location.setIndicatorCurrent(locationDto.getIsCurrent());
        location.setUserAudit(userAudit);

        locationRepository.save(location);
    }
}