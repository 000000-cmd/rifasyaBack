package org.rifasya.main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.rifasya.main.entities.listEntities.lists.ListDocumentType;
import org.rifasya.main.entities.listEntities.lists.ListGenderType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "thirdparties")
public class ThirdParty {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "IdThirdParty", nullable = false)
    private UUID id;

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "SecondName")
    private String secondName;

    @Column(name = "FirstLastName", nullable = false)
    private String firstLastName;

    @Column(name = "SecondLastName")
    private String secondLastName;

    @Column(name = "DocumentNumber", nullable = false, unique = true)
    private String documentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDocumentType", nullable = false)
    private ListDocumentType documentType;

    @Column(name = "BirthDate")
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdGenderType")
    private ListGenderType genderType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUser", nullable = false)
    @JsonIgnoreProperties({"userAudit"})
    private User user;

    @Column(name = "IndicatorEnabled", nullable = false)
    private Boolean indicatorEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserAudit")
    @JsonIgnoreProperties({"userAudit"})
    private User userAudit;

    @Column(name = "AuditDate", nullable = false)
    private LocalDateTime auditDate;

    public ThirdParty() {}

    public ThirdParty(UUID id, String firstName, String secondName, String firstLastName, String secondLastName,
                      String documentNumber, ListDocumentType documentType, ListGenderType genderType, User user,
                      Boolean indicatorEnabled, User userAudit, LocalDateTime auditDate) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.genderType = genderType;
        this.user = user;
        this.indicatorEnabled = indicatorEnabled;
        this.userAudit = userAudit;
        this.auditDate = auditDate;
    }

    @PrePersist
    protected void onCreate() {
        this.auditDate = LocalDateTime.now();
        this.indicatorEnabled = true;
    }

    // Getters y setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getSecondName() { return secondName; }
    public void setSecondName(String secondName) { this.secondName = secondName; }

    public String getFirstLastName() { return firstLastName; }
    public void setFirstLastName(String firstLastName) { this.firstLastName = firstLastName; }

    public String getSecondLastName() { return secondLastName; }
    public void setSecondLastName(String secondLastName) { this.secondLastName = secondLastName; }

    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }

    public ListDocumentType getDocumentType() { return documentType; }
    public void setDocumentType(ListDocumentType documentType) { this.documentType = documentType; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public ListGenderType getGenderType() { return genderType; }
    public void setGenderType(ListGenderType genderType) { this.genderType = genderType; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Boolean getIndicatorEnabled() { return indicatorEnabled; }
    public void setIndicatorEnabled(Boolean indicatorEnabled) { this.indicatorEnabled = indicatorEnabled; }

    public User getUserAudit() { return userAudit; }
    public void setUserAudit(User userAudit) { this.userAudit = userAudit; }

    public LocalDateTime getAuditDate() { return auditDate; }
    public void setAuditDate(LocalDateTime auditDate) { this.auditDate = auditDate; }
}
