package org.rifasya.main.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.rifasya.main.entities.listEntities.ListDocumentType;
import org.rifasya.main.entities.listEntities.ListGenderType;
import org.rifasya.main.repositories.listRepositories.ListDocumentTypeRepository;
import org.rifasya.main.repositories.listRepositories.ListGenderTypeRepository;


@Mapper(componentModel = "spring")
public interface ListTypeMapper {

    // Entity -> Code
    @Named("toCode")
    default String toCode(ListDocumentType docType) {
        return docType != null ? docType.getCode() : null;
    }

    @Named("toCode")
    default String toCode(ListGenderType genderType) {
        return genderType != null ? genderType.getCode() : null;
    }

    // Entity -> Name
    @Named("toName")
    default String toName(ListDocumentType docType) {
        return docType != null ? docType.getName() : null;
    }

    @Named("toName")
    default String toName(ListGenderType genderType) {
        return genderType != null ? genderType.getName() : null;
    }

    // Code -> Entity
    default ListDocumentType toDocumentType(String code, @Context ListDocumentTypeRepository docRepo) {
        if (code == null) return null;
        return docRepo.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
    }

    default ListGenderType toGenderType(String code, @Context ListGenderTypeRepository genderRepo) {
        if (code == null) return null;
        return genderRepo.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Género no encontrado"));
    }
}


