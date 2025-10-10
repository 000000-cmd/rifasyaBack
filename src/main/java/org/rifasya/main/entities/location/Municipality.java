package org.rifasya.main.entities.location;

import jakarta.persistence.*;
import org.rifasya.main.entities.User;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "municipalities")
public class Municipality {

    @Id
    @Column(name = "IdMunicipality", nullable = false)
    private UUID id;

    @Column(name = "Code", nullable = false, unique = true, length = 10)
    private String code;

    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDepartment", nullable = false)
    private Department department;

    @Column(name = "IndicatorEnabled", nullable = false)
    private Boolean indicatorEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserAudit")
    private User userAudit;

    @Column(name = "AuditDate", nullable = false)
    private LocalDateTime auditDate;

    @OneToMany(
            mappedBy = "municipality",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<Neighborhood> neighborhoods = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.id = UUID.randomUUID();
        this.auditDate = LocalDateTime.now();
        this.indicatorEnabled = true;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Boolean getIndicatorEnabled() {
        return indicatorEnabled;
    }

    public void setIndicatorEnabled(Boolean indicatorEnabled) {
        this.indicatorEnabled = indicatorEnabled;
    }

    public User getUserAudit() {
        return userAudit;
    }

    public void setUserAudit(User userAudit) {
        this.userAudit = userAudit;
    }

    public LocalDateTime getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(LocalDateTime auditDate) {
        this.auditDate = auditDate;
    }

    public Set<Neighborhood> getNeighborhoods() {
        return neighborhoods;
    }

    public void setNeighborhoods(Set<Neighborhood> neighborhoods) {
        this.neighborhoods = neighborhoods;
    }
}
