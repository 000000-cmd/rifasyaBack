package org.rifasya.main.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyModel {

    private UUID id;

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
    private UserModel user;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
