package org.rifasya.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.rifasya.main.dto.response.User.UserResponseDTO;
import org.rifasya.main.dto.response.location.LocationResponseDTO;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyResponseDTO {
    private UUID Id;
    private String DocumentNumber;
    private String FirstName;
    private String FirstLastName;
    private String SecondLastName;
    private String SecondName;
    private String DocumentType;
    private LocalDate birthDate;
    private LocationResponseDTO location;
    private String GenderType;
    private UserResponseDTO User;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        DocumentNumber = documentNumber;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getFirstLastName() {
        return FirstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        FirstLastName = firstLastName;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocationResponseDTO getLocation() {
        return location;
    }

    public void setLocation(LocationResponseDTO location) {
        this.location = location;
    }

    public String getGenderType() {
        return GenderType;
    }

    public void setGenderType(String genderType) {
        GenderType = genderType;
    }

    public UserResponseDTO getUser() {
        return User;
    }

    public void setUser(UserResponseDTO user) {
        User = user;
    }
}
