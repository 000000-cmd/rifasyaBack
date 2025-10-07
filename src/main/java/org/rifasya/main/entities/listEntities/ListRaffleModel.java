package org.rifasya.main.entities.listEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.rifasya.main.entities.User;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "listrafflemodels")
public class ListRaffleModel {
    @Id
    @Column(name = "IdList")
    private UUID id;

    @Column(name = "Code", nullable = false, unique = true)
    private String code;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "`Order`", nullable = false)
    private Integer order;

    @Column(name = "IndicatorEnabled", nullable = false)
    private Boolean indicatorEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserAudit")
    @JsonIgnoreProperties({"userAudit"})
    private User userAudit;

    @Column(name = "AuditDate", nullable = false)
    private LocalDateTime auditDate;

    // --- Constructores, Getters, Setters, etc. ---
    public ListRaffleModel() {}

    public ListRaffleModel(UUID id, String code, String name, Integer order, Boolean indicatorEnabled, User userAudit, LocalDateTime auditDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.order = order;
        this.indicatorEnabled = indicatorEnabled;
        this.userAudit = userAudit;
        this.auditDate = auditDate;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getOrder() { return order; }
    public void setOrder(Integer order) { this.order = order; }
    public Boolean getIndicatorEnabled() { return indicatorEnabled; }
    public void setIndicatorEnabled(Boolean indicatorEnabled) { this.indicatorEnabled = indicatorEnabled; }
    public User getUserAudit() { return userAudit; }
    public void setUserAudit(User userAudit) { this.userAudit = userAudit; }
    public LocalDateTime getAuditDate() { return auditDate; }
    public void setAuditDate(LocalDateTime auditDate) { this.auditDate = auditDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListRaffleModel that = (ListRaffleModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}