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

@Mapper(componentModel = "spring", uses = {UserMapper.class, ListTypeMapper.class})
public interface ThirdPartyMapper {

    // DTO -> Model
    @Mapping(target = "genderCode", source = "genderCode", qualifiedByName = "resolveGenderCode")
    @Mapping(target = "documentCode", source = "documentCode", qualifiedByName = "resolveDocumentCode")
    ThirdPartyModel requestToModel(
            ThirdPartyRequestDTO dto,
            @Context ListDocumentTypeRepository docTypeRepo,
            @Context ListGenderTypeRepository genderTypeRepo
    );

    // Model -> Entity
    @Mapping(source = "model.user", target = "user")
    @Mapping(source = "model.id", target = "id")
    @Mapping(target = "documentType", expression = "java(listTypeMapper.toDocumentType(model.getDocumentCode(), docRepo))")
    @Mapping(target = "genderType", expression = "java(listTypeMapper.toGenderType(model.getGenderCode(), genderRepo))")
    ThirdParty modelToEntity(
            ThirdPartyModel model,
            @Context ListDocumentTypeRepository docRepo,
            @Context ListGenderTypeRepository genderRepo,
            User userEntity
    );

    // Entity -> Model
    @Mapping(source = "documentType", target = "documentCode", qualifiedByName = "toCode")
    @Mapping(source = "genderType", target = "genderCode", qualifiedByName = "toCode")
    @Mapping(source = "user", target = "user")
    ThirdPartyModel entityToModel(ThirdParty entity);

    // Model -> ResponseDTO
    @Mapping(source = "user", target = "user")
    @Mapping(target = "documentType", source = "documentCode", qualifiedByName = "resolveDocumentName")
    @Mapping(target = "genderType", source = "genderCode", qualifiedByName = "resolveGenderName")
    ThirdPartyResponseDTO modelToResponseDTO(
            ThirdPartyModel model,
            @Context ListDocumentTypeRepository docRepo,
            @Context ListGenderTypeRepository genderRepo
    );

    // Métodos auxiliares con @Named
    @Named("resolveGenderCode")
    default String resolveGenderCode(String code, @Context ListGenderTypeRepository repo) {
        if (code == null) return null;
        return repo.findByCode(code)
                .map(ListGenderType::getCode)
                .orElse(null);
    }

    @Named("resolveDocumentCode")
    default String resolveDocumentCode(String code, @Context ListDocumentTypeRepository repo) {
        if (code == null) return null;
        return repo.findByCode(code)
                .map(ListDocumentType::getCode)
                .orElse(null);
    }

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



