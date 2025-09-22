package org.rifasya.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rifasya.main.entities.User;
import org.rifasya.main.entities.listEntities.ListDocumentType;
import org.rifasya.main.entities.listEntities.ListGenderType;
import org.rifasya.main.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
public class ThirdPartyResponseDTO {

    private UUID IdThirdParty;
    String DocumentType;
    String DocumentNumber;
    String FirstName;
    String SecondName;
    String FirstLastName;
    String SecondLastName;
    String GenderType;
    UUID User;
    Boolean IndicatorEnabled;

    public ThirdPartyResponseDTO(UUID idThirdParty,
                                 String documentType,
                                 String documentNumber,
                                 String firstName,
                                 String secondName,
                                 String firstLastName,
                                 String secondLastName,
                                 String genderType,
                                 UUID user,
                                 Boolean indicatorEnabled) {
        IdThirdParty = idThirdParty;
        DocumentType = documentType;
        DocumentNumber = documentNumber;
        FirstName = firstName;
        SecondName = secondName;
        FirstLastName = firstLastName;
        SecondLastName = secondLastName;
        GenderType = genderType;
        User = user;
        IndicatorEnabled = indicatorEnabled;
    }

    public UUID getIdThirdParty() {
        return IdThirdParty;
    }

    public void setIdThirdParty(UUID idThirdParty) {
        IdThirdParty = idThirdParty;
    }

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        DocumentNumber = documentNumber;
    }

    public String getFirstLastName() {
        return FirstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        FirstLastName = firstLastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public Boolean getIndicatorEnabled() {
        return IndicatorEnabled;
    }

    public void setIndicatorEnabled(Boolean indicatorEnabled) {
        IndicatorEnabled = indicatorEnabled;
    }

    public String getSecondLastName() {
        return SecondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        SecondLastName = secondLastName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }

    public String getGenderType() {
        return GenderType;
    }

    public void setGenderType(String genderType) {
        GenderType = genderType;
    }

    public UUID getUser() {
        return User;
    }

    public void setUser(UUID user) {
        User = user;
    }
}
