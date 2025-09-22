package org.rifasya.main.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public class ThirdPartyRequestDTO {

    private LocalDateTime AuditDate;
    private String DocumentNumber;
    private String FirstLastName;
    private String FirstName;
    private Boolean IndicatorEnabled;
    private String SecondLastName;
    private String SecondName;
    private String DocumentCode;
    private String GenderCode;
    private UUID IdUser;
    private UUID userAuditId;

    public LocalDateTime getAuditDate() {
        return AuditDate;
    }

    public void setAuditDate(LocalDateTime auditDate) {
        AuditDate = auditDate;
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

    public String getDocumentCode() {
        return DocumentCode;
    }

    public void setDocumentCode(String documentCode) {
        DocumentCode = documentCode;
    }

    public String getGenderCode() {
        return GenderCode;
    }

    public void setGenderCode(String genderCode) {
        GenderCode = genderCode;
    }

    public UUID getIdUser() {
        return IdUser;
    }

    public void setIdUser(UUID idUser) {
        IdUser = idUser;
    }

    public UUID getUserAuditId() {
        return userAuditId;
    }

    public void setUserAuditId(UUID userAuditId) {
        this.userAuditId = userAuditId;
    }
}
