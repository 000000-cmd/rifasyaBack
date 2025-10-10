package org.rifasya.main.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.rifasya.main.dto.request.ThirdPartyRequestDTO;
import org.rifasya.main.dto.response.ThirdPartyResponseDTO;
import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import org.rifasya.main.entities.listEntities.ListDocumentType;
import org.rifasya.main.entities.listEntities.ListGenderType;
import org.rifasya.main.models.ThirdPartyModel;
import org.rifasya.main.repositories.listRepositories.ListDocumentTypeRepository;
import org.rifasya.main.repositories.listRepositories.ListGenderTypeRepository;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ListMapper.class})
public interface ThirdPartyMapper {

    // DTO -> Model
    @Mapping(target = "genderCode", source = "genderCode", qualifiedByName = "resolveGenderCode")
    @Mapping(target = "documentCode", source = "documentCode", qualifiedByName = "resolveDocumentCode")
    @Mapping(target = "birthDate", source = "birthDate")
    ThirdPartyModel requestToModel(
            ThirdPartyRequestDTO dto,
            @Context ListDocumentTypeRepository docTypeRepo,
            @Context ListGenderTypeRepository genderTypeRepo
    );

    // Model -> Entity
    @Mapping(source = "model.user", target = "user")
    @Mapping(source = "model.id", target = "id")
    @Mapping(source = "model.birthDate", target = "birthDate")
    @Mapping(target = "documentType", expression = "java(listMapper.toDocumentType(model.getDocumentCode(), docRepo))")
    @Mapping(target = "genderType", expression = "java(listMapper.toGenderType(model.getGenderCode(), genderRepo))")
    ThirdParty modelToEntity(
            ThirdPartyModel model,
            @Context ListDocumentTypeRepository docRepo,
            @Context ListGenderTypeRepository genderRepo,
            @Context ListMapper listMapper,
            User userEntity
    );

    // Entity -> Model
    @Mapping(source = "documentType", target = "documentCode", qualifiedByName = "documentTypeToCode")
    @Mapping(source = "genderType", target = "genderCode", qualifiedByName = "genderTypeToCode")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "birthDate", target = "birthDate")
    ThirdPartyModel entityToModel(ThirdParty entity);

    // Model -> ResponseDTO
    @Mapping(source = "user", target = "user")
    @Mapping(target = "documentType", source = "documentCode", qualifiedByName = "resolveDocumentName")
    @Mapping(target = "genderType", source = "genderCode", qualifiedByName = "resolveGenderName")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "location", ignore = true)
    ThirdPartyResponseDTO modelToResponseDTO(
            ThirdPartyModel model,
            @Context ListDocumentTypeRepository docRepo,
            @Context ListGenderTypeRepository genderRepo
    );

    // --- Métodos para convertir entidad a código ---
    @Named("documentTypeToCode")
    default String documentTypeToCode(ListDocumentType docType) {
        return (docType != null) ? docType.getCode() : null;
    }

    @Named("genderTypeToCode")
    default String genderTypeToCode(ListGenderType genderType) {
        return (genderType != null) ? genderType.getCode() : null;
    }

    // --- Métodos para validar y resolver códigos desde el DTO ---
    @Named("resolveGenderCode")
    default String resolveGenderCode(String code, @Context ListGenderTypeRepository repo) {
        if (code == null) return null;
        return repo.findByCode(code)
                .map(ListGenderType::getCode)
                .orElseThrow(() -> new RuntimeException("El código de género '" + code + "' no es válido."));
    }

    @Named("resolveDocumentCode")
    default String resolveDocumentCode(String code, @Context ListDocumentTypeRepository repo) {
        if (code == null) return null;
        return repo.findByCode(code)
                .map(ListDocumentType::getCode)
                .orElseThrow(() -> new RuntimeException("El código de documento '" + code + "' no es válido."));
    }

    // --- Métodos para resolver nombres para el DTO de respuesta ---
    @Named("resolveGenderName")
    default String resolveGenderName(String code, @Context ListGenderTypeRepository repo) {
        if (code == null) return null;
        return repo.findByCode(code)
                .map(ListGenderType::getName)
                .orElse(null);
    }

    @Named("resolveDocumentName")
    default String resolveDocumentName(String code, @Context ListDocumentTypeRepository repo) {
        if (code == null) return null;
        return repo.findByCode(code)
                .map(ListDocumentType::getName)
                .orElse(null);
    }
}



