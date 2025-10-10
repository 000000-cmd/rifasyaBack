package org.rifasya.main.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rifasya.main.dto.request.location.*;
import org.rifasya.main.dto.response.location.*;
import org.rifasya.main.entities.location.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    // --- MÉTODOS DE ENTIDAD A DTO DE RESPUESTA ---
    CountryResponseDTO countryToCountryResponseDTO(Country country);
    DepartmentResponseDTO departmentToDepartmentResponseDTO(Department department);
    MunicipalityResponseDTO municipalityToMunicipalityResponseDTO(Municipality municipality);
    NeighborhoodResponseDTO neighborhoodToNeighborhoodResponseDTO(Neighborhood neighborhood);

    // MapStruct genera automáticamente el código para mapear listas
    // si sabe cómo mapear los elementos individuales.
    List<CountryResponseDTO> countriesToCountryResponseDTOs(List<Country> countries);
    List<DepartmentResponseDTO> departmentsToDepartmentResponseDTOs(List<Department> departments);
    List<MunicipalityResponseDTO> municipalitiesToMunicipalityResponseDTOs(List<Municipality> municipalities);
    List<NeighborhoodResponseDTO> neighborhoodsToNeighborhoodResponseDTOs(List<Neighborhood> neighborhoods);


    // --- MÉTODOS DE DTO DE PETICIÓN A ENTIDAD ---
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "departments", ignore = true)
    @Mapping(target = "userAudit", ignore = true)
    @Mapping(target = "auditDate", ignore = true)
    @Mapping(target = "indicatorEnabled", ignore = true)
    Country countryRequestDTOToCountry(CountryRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "municipalities", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "userAudit", ignore = true)
    @Mapping(target = "auditDate", ignore = true)
    @Mapping(target = "indicatorEnabled", ignore = true)
    Department departmentRequestDTOToDepartment(DepartmentRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "neighborhoods", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "userAudit", ignore = true)
    @Mapping(target = "auditDate", ignore = true)
    @Mapping(target = "indicatorEnabled", ignore = true)
    Municipality municipalityRequestDTOToMunicipality(MunicipalityRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "municipality", ignore = true)
    @Mapping(target = "userAudit", ignore = true)
    @Mapping(target = "auditDate", ignore = true)
    @Mapping(target = "indicatorEnabled", ignore = true)
    Neighborhood neighborhoodRequestDTOToNeighborhood(NeighborhoodRequestDTO dto);
}