package org.rifasya.main.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.rifasya.main.dto.request.User.EmbeddedUserRequestDTO;
import org.rifasya.main.dto.request.location.LocationRequestDTO;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyRequestDTO {
    @NotBlank
    private String firstName;
    private String secondName;

    @NotBlank
    private String firstLastName;
    private String secondLastName;

    @NotBlank
    private String documentNumber;

    @NotBlank
    private String documentCode;
    private String genderCode;

    private LocalDate birthDate;

    @Valid
    @NotNull
    private LocationRequestDTO location;

    @Valid
    private EmbeddedUserRequestDTO user;

    public @NotBlank String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public @NotBlank String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(@NotBlank String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public @NotBlank String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(@NotBlank String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public @NotBlank String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(@NotBlank String documentCode) {
        this.documentCode = documentCode;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocationRequestDTO getLocation() {
        return location;
    }

    public void setLocation(LocationRequestDTO location) {
        this.location = location;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public @Valid EmbeddedUserRequestDTO getUser() {
        return user;
    }

    public void setUser(@Valid EmbeddedUserRequestDTO user) {
        this.user = user;
    }
}

