package org.rifasya.main.controllers;

import jakarta.validation.Valid;
import org.rifasya.main.dto.request.location.*;
import org.rifasya.main.dto.response.location.*;
import org.rifasya.main.dto.response.location.projection.LocationSearchDTO;
import org.rifasya.main.entities.User;
import org.rifasya.main.exceptions.ResourceNotFoundException;
import org.rifasya.main.repositories.UserRepository;
import org.rifasya.main.services.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;
    private final UserRepository userRepository;

    public LocationController(LocationService locationService, UserRepository userRepository) {
        this.locationService = locationService;
        this.userRepository = userRepository;
    }

    // --- ENDPOINTS DE BÚSQUEDA Y ÁRBOL ANIDADO ---

    @GetMapping("/tree")
    public ResponseEntity<List<CountryResponseDTO>> getNestedLocations() {
        return ResponseEntity.ok(locationService.getNestedLocations());
    }

    @GetMapping("/countries/search")
    public ResponseEntity<List<CountryResponseDTO>> searchCountries(@RequestParam String name) {
        return ResponseEntity.ok(locationService.searchCountries(name));
    }

    @GetMapping("/departments/search")
    public ResponseEntity<List<DepartmentResponseDTO>> searchDepartments(@RequestParam String name) {
        return ResponseEntity.ok(locationService.searchDepartments(name));
    }

    @GetMapping("/municipalities/search")
    public ResponseEntity<List<MunicipalityResponseDTO>> searchMunicipalities(@RequestParam String name) {
        return ResponseEntity.ok(locationService.searchMunicipalities(name));
    }

    @GetMapping("/neighborhoods/search")
    public ResponseEntity<List<NeighborhoodResponseDTO>> searchNeighborhoods(@RequestParam String name) {
        return ResponseEntity.ok(locationService.searchNeighborhoods(name));
    }

    // --- ENDPOINTS DE ESTADO (MODIFICADOS) ---

    @PatchMapping("/countries/{id}/status")
    public ResponseEntity<Void> updateCountryStatus(@PathVariable UUID id, @RequestParam boolean enabled, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = getCurrentUser(userDetails);
        locationService.updateCountryStatus(id, enabled, currentUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/departments/{id}/status")
    public ResponseEntity<Void> updateDepartmentStatus(@PathVariable UUID id, @RequestParam boolean enabled, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = getCurrentUser(userDetails);
        locationService.updateDepartmentStatus(id, enabled, currentUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/municipalities/{id}/status")
    public ResponseEntity<Void> updateMunicipalityStatus(@PathVariable UUID id, @RequestParam boolean enabled, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = getCurrentUser(userDetails);
        locationService.updateMunicipalityStatus(id, enabled, currentUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/neighborhoods/{id}/status")
    public ResponseEntity<Void> updateNeighborhoodStatus(@PathVariable UUID id, @RequestParam boolean enabled, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = getCurrentUser(userDetails);
        locationService.updateNeighborhoodStatus(id, enabled, currentUser);
        return ResponseEntity.ok().build();
    }

    private User getCurrentUser(UserDetails userDetails) {
        if (userDetails == null) {
            throw new ResourceNotFoundException("No se encontró información de autenticación.");
        }
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario auditor no encontrado: " + userDetails.getUsername()));
    }

    // --- ENDPOINTS GET ALL (PLANOS Y RÁPIDOS) ---

    @GetMapping("/countries")
    public ResponseEntity<List<LocationSearchDTO>> getAllCountries() {
        return ResponseEntity.ok(locationService.getAllCountries());
    }

    @GetMapping("/departments")
    public ResponseEntity<List<LocationSearchDTO>> getAllDepartments() {
        return ResponseEntity.ok(locationService.getAllDepartments());
    }

    @GetMapping("/municipalities")
    public ResponseEntity<List<LocationSearchDTO>> getAllMunicipalities() {
        return ResponseEntity.ok(locationService.getAllMunicipalities());
    }

    @GetMapping("/neighborhoods")
    public ResponseEntity<List<LocationSearchDTO>> getAllNeighborhoods() {
        return ResponseEntity.ok(locationService.getAllNeighborhoods());
    }

    // --- ENDPOINTS DE BÚSQUEDA RÁPIDA ---
    @GetMapping("/countries/fa/search")
    public ResponseEntity<List<LocationSearchDTO>> fastSearchCountries(@RequestParam String name) {
        return ResponseEntity.ok(locationService.fastSearchCountries(name));
    }

    @GetMapping("/departments/fa/search")
    public ResponseEntity<List<LocationSearchDTO>> fastSearchDepartments(@RequestParam String name) {
        return ResponseEntity.ok(locationService.fastSearchDepartments(name));
    }

    @GetMapping("/municipalities/fa/search")
    public ResponseEntity<List<LocationSearchDTO>> fastSearchMunicipalities(@RequestParam String name) {
        return ResponseEntity.ok(locationService.fastSearchMunicipalities(name));
    }

    @GetMapping("/neighborhoods/fa/search")
    public ResponseEntity<List<LocationSearchDTO>> fastSearchNeighborhoods(@RequestParam String name) {
        return ResponseEntity.ok(locationService.fastSearchNeighborhoods(name));
    }

    // --- ENDPOINTS CRUD: COUNTRIES ---

    @PostMapping("/countries")
    public ResponseEntity<CountryResponseDTO> createCountry(@Valid @RequestBody CountryRequestDTO dto) {
        return new ResponseEntity<>(locationService.createCountry(dto), HttpStatus.CREATED);
    }

    @GetMapping("/countries/tree")
    public ResponseEntity<List<CountryResponseDTO>> getAllTreeCountries() {
        return ResponseEntity.ok(locationService.getAllTreeCountries());
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<CountryResponseDTO> getCountryById(@PathVariable UUID id) {
        return ResponseEntity.ok(locationService.getCountryById(id));
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<CountryResponseDTO> updateCountry(@PathVariable UUID id, @Valid @RequestBody CountryRequestDTO dto) {
        return ResponseEntity.ok(locationService.updateCountry(id, dto));
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable UUID id) {
        locationService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS CRUD: DEPARTMENTS ---

    @PostMapping("/departments")
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@Valid @RequestBody DepartmentRequestDTO dto) {
        return new ResponseEntity<>(locationService.createDepartment(dto), HttpStatus.CREATED);
    }

    @GetMapping("/departments/tree")
    public ResponseEntity<List<DepartmentResponseDTO>> getAllTreeDepartments() {
        return ResponseEntity.ok(locationService.getAllTreeDepartments());
    }


    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(locationService.getDepartmentById(id));
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable UUID id, @Valid @RequestBody DepartmentRequestDTO dto) {
        return ResponseEntity.ok(locationService.updateDepartment(id, dto));
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable UUID id) {
        locationService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS PARA LISTAS DEPENDIENTES ---

    /**
     * Devuelve una lista de todos los departamentos de un país específico.
     * GET /api/locations/countries/{countryId}/departments
     */
    @GetMapping("/countries/{countryId}/departments")
    public ResponseEntity<List<LocationSearchDTO>> getDepartmentsByCountry(@PathVariable UUID countryId) {
        return ResponseEntity.ok(locationService.getDepartmentsByCountry(countryId));
    }

    /**
     * Devuelve una lista de todos los municipios de un departamento específico.
     * GET /api/locations/departments/{departmentId}/municipalities
     */
    @GetMapping("/departments/{departmentId}/municipalities")
    public ResponseEntity<List<LocationSearchDTO>> getMunicipalitiesByDepartment(@PathVariable UUID departmentId) {
        return ResponseEntity.ok(locationService.getMunicipalitiesByDepartment(departmentId));
    }

    /**
     * Devuelve una lista de todos los barrios/veredas de un municipio específico.
     * GET /api/locations/municipalities/{municipalityId}/neighborhoods
     */
    @GetMapping("/municipalities/{municipalityId}/neighborhoods")
    public ResponseEntity<List<LocationSearchDTO>> getNeighborhoodsByMunicipality(@PathVariable UUID municipalityId) {
        return ResponseEntity.ok(locationService.getNeighborhoodsByMunicipality(municipalityId));
    }


    // --- ENDPOINTS CRUD: MUNICIPALITIES ---

    @PostMapping("/municipalities")
    public ResponseEntity<MunicipalityResponseDTO> createMunicipality(@Valid @RequestBody MunicipalityRequestDTO dto) {
        return new ResponseEntity<>(locationService.createMunicipality(dto), HttpStatus.CREATED);
    }

    @GetMapping("/municipalities/tree")
    public ResponseEntity<List<MunicipalityResponseDTO>> getAllTreeMunicipalities() {
        return ResponseEntity.ok(locationService.getAllTreeMunicipalities());
    }

    @GetMapping("/municipalities/{id}")
    public ResponseEntity<MunicipalityResponseDTO> getMunicipalityById(@PathVariable UUID id) {
        return ResponseEntity.ok(locationService.getMunicipalityById(id));
    }

    @PutMapping("/municipalities/{id}")
    public ResponseEntity<MunicipalityResponseDTO> updateMunicipality(@PathVariable UUID id, @Valid @RequestBody MunicipalityRequestDTO dto) {
        return ResponseEntity.ok(locationService.updateMunicipality(id, dto));
    }

    @DeleteMapping("/municipalities/{id}")
    public ResponseEntity<Void> deleteMunicipality(@PathVariable UUID id) {
        locationService.deleteMunicipality(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS CRUD: NEIGHBORHOODS ---

    @PostMapping("/neighborhoods")
    public ResponseEntity<NeighborhoodResponseDTO> createNeighborhood(@Valid @RequestBody NeighborhoodRequestDTO dto) {
        return new ResponseEntity<>(locationService.createNeighborhood(dto), HttpStatus.CREATED);
    }

    @GetMapping("/neighborhoods/tree")
    public ResponseEntity<List<NeighborhoodResponseDTO>> getAllTreeNeighborhoods() {
        return ResponseEntity.ok(locationService.getAllTreeNeighborhoods());
    }

    @GetMapping("/neighborhoods/{id}")
    public ResponseEntity<NeighborhoodResponseDTO> getNeighborhoodById(@PathVariable UUID id) {
        return ResponseEntity.ok(locationService.getNeighborhoodById(id));
    }

    @PutMapping("/neighborhoods/{id}")
    public ResponseEntity<NeighborhoodResponseDTO> updateNeighborhood(@PathVariable UUID id, @Valid @RequestBody NeighborhoodRequestDTO dto) {
        return ResponseEntity.ok(locationService.updateNeighborhood(id, dto));
    }

    @DeleteMapping("/neighborhoods/{id}")
    public ResponseEntity<Void> deleteNeighborhood(@PathVariable UUID id) {
        locationService.deleteNeighborhood(id);
        return ResponseEntity.noContent().build();
    }
}
