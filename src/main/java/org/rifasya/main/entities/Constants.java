package org.rifasya.main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "constants")
public class Constants {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Es buena práctica generar el UUID
    @Column(name = "IdConstant")
    private UUID id;

    @Column(name = "Code", unique = true, nullable = false) // El código debe ser único y no nulo
    private String code;

    @Column(name = "Description")
    private String description;

    @Column(name = "Value")
    private String value;

    @Column(name = "IndicatorEnabled", nullable = false)
    private Boolean indicatorEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserAudit")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User userAudit;

    @Column(name = "AuditDate", nullable = false)
    private LocalDateTime auditDate;

    // --- Getters y Setters ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public Boolean getIndicatorEnabled() { return indicatorEnabled; }
    public void setIndicatorEnabled(Boolean indicatorEnabled) { this.indicatorEnabled = indicatorEnabled; }

    public User getUserAudit() { return userAudit; }
    public void setUserAudit(User userAudit) { this.userAudit = userAudit; }

    public LocalDateTime getAuditDate() { return auditDate; }
    public void setAuditDate(LocalDateTime auditDate) { this.auditDate = auditDate; }
}