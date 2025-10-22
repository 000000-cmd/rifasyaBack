package org.rifasya.main.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.rifasya.main.dto.request.list.ListRegistryRequestDTO;
import org.rifasya.main.dto.request.list.ListTypeRequestDTO;
import org.rifasya.main.dto.response.ListRegistryResponseDTO;
import org.rifasya.main.dto.response.ListTypeResponseDTO;
import org.rifasya.main.entities.listEntities.lists.*;
import org.rifasya.main.entities.meta.ListRegistry;
import org.rifasya.main.repositories.listRepositories.ListDocumentTypeRepository;
import org.rifasya.main.repositories.listRepositories.ListGenderTypeRepository;

/**
 * Interfaz de Mapper que utiliza MapStruct para convertir entre DTOs y Entidades.
 * Centraliza toda la lógica de mapeo para las listas.
 */
@Mapper(componentModel = "spring")
public interface ListMapper {

    /**
     * Convierte un código de String a una entidad ListDocumentType.
     * Es utilizado por otros mappers para resolver relaciones.
     */
    default ListDocumentType toDocumentType(String code, @Context ListDocumentTypeRepository docRepo) {
        if (code == null) {
            return null;
        }
        return docRepo.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado con código: " + code));
    }

    /**
     * Convierte un código de String a una entidad ListGenderType.
     * Es utilizado por otros mappers para resolver relaciones.
     */
    default ListGenderType toGenderType(String code, @Context ListGenderTypeRepository genderRepo) {
        if (code == null) {
            return null;
        }
        return genderRepo.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Tipo de género no encontrado con código: " + code));
    }

    // --- Mapeo Genérico de cualquier Entidad de Lista a ListTypeResponseDTO ---
    // MapStruct es lo suficientemente inteligente para mapear campos con nombres similares (ej: id -> idList).
    ListTypeResponseDTO toResponseDTO(ListCategory entity);
    ListTypeResponseDTO toResponseDTO(ListDocumentType entity);
    ListTypeResponseDTO toResponseDTO(ListGenderType entity);
    ListTypeResponseDTO toResponseDTO(ListRole entity);
    ListTypeResponseDTO toResponseDTO(ListExternalLottery entity);
    ListTypeResponseDTO toResponseDTO(ListDrawMethod entity);
    ListTypeResponseDTO toResponseDTO(ListRaffleModel entity);
    ListTypeResponseDTO toResponseDTO(ListPrizeType entity);
    ListTypeResponseDTO toResponseDTO(ListRaffleRule entity);

    // --- Mapeos Específicos para ListRegistry ---
    ListRegistry toEntity(ListRegistryRequestDTO dto);
    ListRegistryResponseDTO toResponseDTO(ListRegistry entity);
    void updateFromDto(ListRegistryRequestDTO dto, @MappingTarget ListRegistry entity);

    // --- Mapeos de ListTypeRequestDTO a cada Entidad Específica ---
    ListCategory toCategoryEntity(ListTypeRequestDTO dto);
    ListDocumentType toDocumentTypeEntity(ListTypeRequestDTO dto);
    ListGenderType toGenderTypeEntity(ListTypeRequestDTO dto);
    ListRole toRoleTypeEntity(ListTypeRequestDTO dto);
    ListExternalLottery toExternalLotteryEntity(ListTypeRequestDTO dto);
    ListDrawMethod toDrawMethodEntity(ListTypeRequestDTO dto);
    ListRaffleModel toRaffleModelEntity(ListTypeRequestDTO dto);
    ListPrizeType toPrizeTypeEntity(ListTypeRequestDTO dto);
    ListRaffleRule toRaffleRuleEntity(ListTypeRequestDTO dto);

    // --- Mapeos para Actualizar cada Entidad Específica desde un ListTypeRequestDTO ---
    // @MappingTarget indica a MapStruct que actualice la instancia 'entity' en lugar de crear una nueva.
    void updateCategoryFromDto(ListTypeRequestDTO dto, @MappingTarget ListCategory entity);
    void updateDocumentTypeFromDto(ListTypeRequestDTO dto, @MappingTarget ListDocumentType entity);
    void updateGenderTypeFromDto(ListTypeRequestDTO dto, @MappingTarget ListGenderType entity);
    void updateRoleTypeFromDto(ListTypeRequestDTO dto, @MappingTarget ListRole entity);
    void updateExternalLotteryFromDto(ListTypeRequestDTO dto, @MappingTarget ListExternalLottery entity);
    void updateDrawMethodFromDto(ListTypeRequestDTO dto, @MappingTarget ListDrawMethod entity);
    void updateRaffleModelFromDto(ListTypeRequestDTO dto, @MappingTarget ListRaffleModel entity);
    void updatePrizeTypeFromDto(ListTypeRequestDTO dto, @MappingTarget ListPrizeType entity);
    void updateRaffleRuleFromDto(ListTypeRequestDTO dto, @MappingTarget ListRaffleRule entity);
}

