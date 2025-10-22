package org.rifasya.main.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.rifasya.main.dto.request.ConstantsRequestDTO;
import org.rifasya.main.dto.response.ConstantsResponseDTO;
import org.rifasya.main.entities.Constants;

@Mapper(componentModel = "spring")
public interface ConstantsMapper {

    /**
     * Mapea una entidad Constants a su DTO de respuesta.
     */
    @Mapping(source = "userAudit.username", target = "userAuditUsername")
    ConstantsResponseDTO toResponseDTO(Constants constant);

    /**
     * Mapea un DTO de petición a una nueva entidad Constants.
     */
    Constants toEntity(ConstantsRequestDTO dto);

    /**
     * Actualiza una entidad existente a partir de un DTO de petición.
     */
    void updateEntityFromDto(ConstantsRequestDTO dto, @MappingTarget Constants entity);
}
