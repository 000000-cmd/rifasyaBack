package org.rifasya.main.services;

import org.rifasya.main.dto.request.location.*;
import org.rifasya.main.dto.response.location.*;
import org.rifasya.main.dto.response.location.projection.LocationSearchDTO;
import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import org.rifasya.main.entities.location.*;
import org.rifasya.main.exceptions.BadRequestException;
import org.rifasya.main.exceptions.DuplicateResourceException;
import org.rifasya.main.exceptions.ResourceNotFoundException;
import org.rifasya.main.mappers.LocationMapper;
import org.rifasya.main.repositories.ThirdPartyRepository;
import org.rifasya.main.repositories.location.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class LocationService {

    private final CountryRepository countryRepository;
    private final DepartmentRepository departmentRepository;
    private final MunicipalityRepository municipalityRepository;
    private final NeighborhoodRepository neighborhoodRepository;
    private final ThirdPartyRepository thirdPartyRepository;
    private final ThirdPartyLocationRepository thirdPartyLocationRepository;
    private final LocationMapper locationMapper;

    public LocationService(CountryRepository countryRepository, DepartmentRepository departmentRepository, MunicipalityRepository municipalityRepository, NeighborhoodRepository neighborhoodRepository, ThirdPartyRepository thirdPartyRepository, ThirdPartyLocationRepository thirdPartyLocationRepository, LocationMapper locationMapper) {
        this.countryRepository = countryRepository;
        this.departmentRepository = departmentRepository;
        this.municipalityRepository = municipalityRepository;
        this.neighborhoodRepository = neighborhoodRepository;
        this.thirdPartyRepository = thirdPartyRepository;
        this.thirdPartyLocationRepository = thirdPartyLocationRepository;
        this.locationMapper = locationMapper;
    }

    // --- BÚSQUEDA Y ÁRBOL ANIDADO ---

    @Transactional(readOnly = true)
    public List<CountryResponseDTO> getNestedLocations() {
        var countries = countryRepository.findAllWithNestedDetails();
        return locationMapper.countriesToCountryResponseDTOs(countries);
    }

    private <T> List<T> searchByName(String name, SearchFunction<T> function) {
        if (name == null || name.trim().length() < 3) {
            return Collections.emptyList();
        }
        return function.search(name);
    }

    @FunctionalInterface
    interface SearchFunction<T> {
        List<T> search(String name);
    }

    @Transactional(readOnly = true)
    public List<CountryResponseDTO> searchCountries(String name) {
        return searchByName(name, n -> locationMapper.countriesToCountryResponseDTOs(countryRepository.findByNameStartingWithIgnoreCase(n)));
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponseDTO> searchDepartments(String name) {
        return searchByName(name, n -> locationMapper.departmentsToDepartmentResponseDTOs(departmentRepository.findByNameStartingWithIgnoreCase(n)));
    }

    @Transactional(readOnly = true)
    public List<MunicipalityResponseDTO> searchMunicipalities(String name) {
        return searchByName(name, n -> locationMapper.municipalitiesToMunicipalityResponseDTOs(municipalityRepository.findByNameStartingWithIgnoreCase(n)));
    }

    @Transactional(readOnly = true)
    public List<NeighborhoodResponseDTO> searchNeighborhoods(String name) {
        return searchByName(name, n -> locationMapper.neighborhoodsToNeighborhoodResponseDTOs(neighborhoodRepository.findByNameStartingWithIgnoreCase(n)));
    }

    // --- LÓGICA PARA ASIGNAR DIRECCIÓN A TERCERO ---

    @Transactional
    public void addOrUpdateLocationToThirdParty(UUID thirdPartyId, LocationRequestDTO locationDto, User userAudit) {
        ThirdParty thirdParty = thirdPartyRepository.findById(thirdPartyId)
                .orElseThrow(() -> new ResourceNotFoundException("El tercero con ID '" + thirdPartyId + "' no fue encontrado."));

        if (locationDto.getIsCurrent()) {
            thirdPartyLocationRepository.deactivateAllCurrentLocationsForThirdParty(thirdPartyId);
        }

        Neighborhood neighborhood = neighborhoodRepository.findByCode(locationDto.getNeighborhoodCode())
                .orElseThrow(() -> new ResourceNotFoundException("Barrio/Vereda con código '" + locationDto.getNeighborhoodCode() + "' no fue encontrado."));

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

        thirdPartyLocationRepository.save(location);
    }

    // --- MÉTODOS DE BÚSQUEDA RÁPIDA (SIMPLIFICADOS) ---
    @Transactional(readOnly = true)
    public List<LocationSearchDTO> fastSearchCountries(String name) {
        if (name == null || name.trim().length() < 3) {
            return Collections.emptyList();
        }
        return countryRepository.findAsProjectionByNameStartingWith(name);
    }

    @Transactional(readOnly = true)
    public List<LocationSearchDTO> fastSearchDepartments(String name) {
        if (name == null || name.trim().length() < 3) {
            return Collections.emptyList();
        }
        return departmentRepository.findAsProjectionByNameStartingWith(name);
    }

    @Transactional(readOnly = true)
    public List<LocationSearchDTO> fastSearchMunicipalities(String name) {
        if (name == null || name.trim().length() < 3) {
            return Collections.emptyList();
        }
        return municipalityRepository.findAsProjectionByNameStartingWith(name);
    }

    @Transactional(readOnly = true)
    public List<LocationSearchDTO> fastSearchNeighborhoods(String name) {
        if (name == null || name.trim().length() < 3) {
            return Collections.emptyList();
        }
        return neighborhoodRepository.findAsProjectionByNameStartingWith(name);
    }

    // --- LÓGICA PARA HABILITAR / DESHABILITAR ---

    @Transactional
    public void updateCountryStatus(UUID id, boolean enabled, User userAudit) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + id));

        country.setIndicatorEnabled(enabled);
        country.setUserAudit(userAudit);
        country.setAuditDate(LocalDateTime.now());
        countryRepository.save(country);

        if (!enabled) {
            // Asumiendo que tienes `findByCountry` en tu DepartmentRepository
            departmentRepository.findByCountry(country).forEach(department ->
                    updateDepartmentStatus(department.getId(), false, userAudit)
            );
        }
    }

    @Transactional
    public void updateDepartmentStatus(UUID id, boolean enabled, User userAudit) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));

        department.setIndicatorEnabled(enabled);
        department.setUserAudit(userAudit);
        department.setAuditDate(LocalDateTime.now());
        departmentRepository.save(department);

        if (!enabled) {
            // Asumiendo que tienes `findByDepartment` en tu MunicipalityRepository
            municipalityRepository.findByDepartment(department).forEach(municipality ->
                    updateMunicipalityStatus(municipality.getId(), false, userAudit)
            );
        }
    }

    @Transactional
    public void updateMunicipalityStatus(UUID id, boolean enabled, User userAudit) {
        Municipality municipality = municipalityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + id));

        municipality.setIndicatorEnabled(enabled);
        municipality.setUserAudit(userAudit);
        municipality.setAuditDate(LocalDateTime.now());
        municipalityRepository.save(municipality);

        if (!enabled) {
            // Asumiendo que tienes `findByMunicipality` en tu NeighborhoodRepository
            neighborhoodRepository.findByMunicipality(municipality).forEach(neighborhood ->
                    updateNeighborhoodStatus(neighborhood.getId(), false, userAudit)
            );
        }
    }

    @Transactional
    public void updateNeighborhoodStatus(UUID id, boolean enabled, User userAudit) {
        Neighborhood neighborhood = neighborhoodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barrio no encontrado con ID: " + id));

        neighborhood.setIndicatorEnabled(enabled);
        neighborhood.setUserAudit(userAudit);
        neighborhood.setAuditDate(LocalDateTime.now());
        neighborhoodRepository.save(neighborhood);
    }


    // --- CRUD: COUNTRIES ---

    @Transactional
    public CountryResponseDTO createCountry(CountryRequestDTO dto) {
        if (countryRepository.existsByCode(dto.getCode())) {
            throw new DuplicateResourceException("El código de país '" + dto.getCode() + "' ya existe.");
        }
        Country country = locationMapper.countryRequestDTOToCountry(dto);
        return locationMapper.countryToCountryResponseDTO(countryRepository.save(country));
    }

    @Transactional(readOnly = true)
    public List<CountryResponseDTO> getAllTreeCountries() {
        return locationMapper.countriesToCountryResponseDTOs(countryRepository.findAll());
    }

    @Transactional(readOnly = true)
    public CountryResponseDTO getCountryById(UUID id) {
        return countryRepository.findById(id)
                .map(locationMapper::countryToCountryResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + id));
    }

    @Transactional
    public CountryResponseDTO updateCountry(UUID id, CountryRequestDTO dto) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + id));
        country.setCode(dto.getCode());
        country.setName(dto.getName());
        return locationMapper.countryToCountryResponseDTO(countryRepository.save(country));
    }

    @Transactional
    public void deleteCountry(UUID id) {
        if (!countryRepository.existsById(id)) {
            throw new ResourceNotFoundException("País no encontrado con ID: " + id);
        }
        if (departmentRepository.countByCountryId(id) > 0) {
            throw new BadRequestException("No se puede eliminar el país porque tiene departamentos asociados.");
        }
        countryRepository.deleteById(id);
    }

    // --- MÉTODOS PARA OBTENER HIJOS POR PADRE ---

    @Transactional(readOnly = true)
    public List<LocationSearchDTO> getDepartmentsByCountry(UUID countryId) {
        if (!countryRepository.existsById(countryId)) {
            throw new ResourceNotFoundException("País no encontrado con ID: " + countryId);
        }
        return departmentRepository.findByCountryIdAsProjection(countryId);
    }

    @Transactional(readOnly = true)
    public List<LocationSearchDTO> getMunicipalitiesByDepartment(UUID departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new ResourceNotFoundException("Departamento no encontrado con ID: " + departmentId);
        }
        return municipalityRepository.findByDepartmentIdAsProjection(departmentId);
    }

    @Transactional(readOnly = true)
    public List<LocationSearchDTO> getNeighborhoodsByMunicipality(UUID municipalityId) {
        if (!municipalityRepository.existsById(municipalityId)) {
            throw new ResourceNotFoundException("Municipio no encontrado con ID: " + municipalityId);
        }
        return neighborhoodRepository.findByMunicipalityIdAsProjection(municipalityId);
    }


    // --- CRUD: DEPARTMENTS ---

    @Transactional
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto) {
        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + dto.getCountryId()));
        if(departmentRepository.existsByCode(dto.getCode())){
            throw new DuplicateResourceException("El código de departamento '" + dto.getCode() + "' ya existe.");
        }
        Department department = locationMapper.departmentRequestDTOToDepartment(dto);
        department.setCountry(country);
        return locationMapper.departmentToDepartmentResponseDTO(departmentRepository.save(department));
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponseDTO> getAllTreeDepartments() {
        return locationMapper.departmentsToDepartmentResponseDTOs(departmentRepository.findAll());
    }

    @Transactional(readOnly = true)
    public DepartmentResponseDTO getDepartmentById(UUID id) {
        return departmentRepository.findById(id)
                .map(locationMapper::departmentToDepartmentResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));
    }

    @Transactional
    public DepartmentResponseDTO updateDepartment(UUID id, DepartmentRequestDTO dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + id));
        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("País no encontrado con ID: " + dto.getCountryId()));
        department.setCode(dto.getCode());
        department.setName(dto.getName());
        department.setCountry(country);
        return locationMapper.departmentToDepartmentResponseDTO(departmentRepository.save(department));
    }

    @Transactional
    public void deleteDepartment(UUID id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Departamento no encontrado con ID: " + id);
        }
        if (municipalityRepository.countByDepartmentId(id) > 0) {
            throw new BadRequestException("No se puede eliminar el departamento porque tiene municipios asociados.");
        }
        departmentRepository.deleteById(id);
    }

    // --- CRUD: MUNICIPALITIES ---

    @Transactional
    public MunicipalityResponseDTO createMunicipality(MunicipalityRequestDTO dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + dto.getDepartmentId()));
        if(municipalityRepository.existsByCode(dto.getCode())){
            throw new DuplicateResourceException("El código de municipio '" + dto.getCode() + "' ya existe.");
        }
        Municipality municipality = locationMapper.municipalityRequestDTOToMunicipality(dto);
        municipality.setDepartment(department);
        return locationMapper.municipalityToMunicipalityResponseDTO(municipalityRepository.save(municipality));
    }

    @Transactional(readOnly = true)
    public List<MunicipalityResponseDTO> getAllTreeMunicipalities() {
        return locationMapper.municipalitiesToMunicipalityResponseDTOs(municipalityRepository.findAll());
    }

    @Transactional(readOnly = true)
    public MunicipalityResponseDTO getMunicipalityById(UUID id) {
        return municipalityRepository.findById(id)
                .map(locationMapper::municipalityToMunicipalityResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + id));
    }

    @Transactional
    public MunicipalityResponseDTO updateMunicipality(UUID id, MunicipalityRequestDTO dto) {
        Municipality municipality = municipalityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + id));
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + dto.getDepartmentId()));
        municipality.setCode(dto.getCode());
        municipality.setName(dto.getName());
        municipality.setDepartment(department);
        return locationMapper.municipalityToMunicipalityResponseDTO(municipalityRepository.save(municipality));
    }

    @Transactional
    public void deleteMunicipality(UUID id) {
        if (!municipalityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Municipio no encontrado con ID: " + id);
        }
        if (neighborhoodRepository.countByMunicipalityId(id) > 0) {
            throw new BadRequestException("No se puede eliminar el municipio porque tiene barrios asociados.");
        }
        municipalityRepository.deleteById(id);
    }

    // --- MÉTODOS "GET ALL" OPTIMIZADOS ---

    @Transactional(readOnly = true)
    public List<LocationSearchDTO> getAllCountries() {
        return countryRepository.findAllAsProjection();
    }

    @Transactional(readOnly = true)
    public List<LocationSearchDTO> getAllDepartments() {
        return departmentRepository.findAllAsProjection();
    }

    @Transactional(readOnly = true)
    public List<LocationSearchDTO> getAllMunicipalities() {
        return municipalityRepository.findAllAsProjection();
    }

    @Transactional(readOnly = true)
    public List<LocationSearchDTO> getAllNeighborhoods() {
        return neighborhoodRepository.findAllAsProjection();
    }

    // --- CRUD: NEIGHBORHOODS ---

    @Transactional
    public NeighborhoodResponseDTO createNeighborhood(NeighborhoodRequestDTO dto) {
        Municipality municipality = municipalityRepository.findById(dto.getMunicipalityId())
                .orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + dto.getMunicipalityId()));
        if(neighborhoodRepository.existsByCode(dto.getCode())){
            throw new DuplicateResourceException("El código de barrio '" + dto.getCode() + "' ya existe.");
        }
        Neighborhood neighborhood = locationMapper.neighborhoodRequestDTOToNeighborhood(dto);
        neighborhood.setMunicipality(municipality);
        return locationMapper.neighborhoodToNeighborhoodResponseDTO(neighborhoodRepository.save(neighborhood));
    }

    @Transactional(readOnly = true)
    public List<NeighborhoodResponseDTO> getAllTreeNeighborhoods() {
        return locationMapper.neighborhoodsToNeighborhoodResponseDTOs(neighborhoodRepository.findAll());
    }

    @Transactional(readOnly = true)
    public NeighborhoodResponseDTO getNeighborhoodById(UUID id) {
        return neighborhoodRepository.findById(id)
                .map(locationMapper::neighborhoodToNeighborhoodResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Barrio no encontrado con ID: " + id));
    }

    @Transactional
    public NeighborhoodResponseDTO updateNeighborhood(UUID id, NeighborhoodRequestDTO dto) {
        Neighborhood neighborhood = neighborhoodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barrio no encontrado con ID: " + id));
        Municipality municipality = municipalityRepository.findById(dto.getMunicipalityId())
                .orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con ID: " + dto.getMunicipalityId()));
        neighborhood.setCode(dto.getCode());
        neighborhood.setName(dto.getName());
        neighborhood.setMunicipality(municipality);
        return locationMapper.neighborhoodToNeighborhoodResponseDTO(neighborhoodRepository.save(neighborhood));
    }

    @Transactional
    public void deleteNeighborhood(UUID id) {
        if (!neighborhoodRepository.existsById(id)) {
            throw new ResourceNotFoundException("Barrio no encontrado con ID: " + id);
        }
        neighborhoodRepository.deleteById(id);
    }
}