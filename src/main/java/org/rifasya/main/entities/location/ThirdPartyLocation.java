package org.rifasya.main.entities.location;

import jakarta.persistence.*;
import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "thirdpartieslocations")
public class ThirdPartyLocation {

    @Id
    @Column(name = "IdThirdPartyLocation", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdThirdParty", nullable = false)
    private ThirdParty thirdParty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdCountry", nullable = false)
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDepartment", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdMunicipality", nullable = false)
    private Municipality municipality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdNeighborhood")
    private Neighborhood neighborhood;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "AddressComplement")
    private String addressComplement;

    @Column(name = "IndicatorCurrent", nullable = false)
    private Boolean indicatorCurrent;

    @Column(name = "IndicatorEnabled", nullable = false)
    private Boolean indicatorEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserAudit")
    private User userAudit;

    @Column(name = "AuditDate", nullable = false)
    private LocalDateTime auditDate;

    @PrePersist
    protected void onCreate() {
        this.id = UUID.randomUUID();
        this.auditDate = LocalDateTime.now();
        this.indicatorEnabled = true;
    }

    public ThirdPartyLocation() {}

    public ThirdPartyLocation(UUID id, ThirdParty thirdParty, Country country, Department department, Municipality municipality, Neighborhood neighborhood, String address, String addressComplement, Boolean indicatorCurrent, Boolean indicatorEnabled, User userAudit, LocalDateTime auditDate) {
        this.id = id;
        this.thirdParty = thirdParty;
        this.country = country;
        this.department = department;
        this.municipality = municipality;
        this.neighborhood = neighborhood;
        this.address = address;
        this.addressComplement = addressComplement;
        this.indicatorCurrent = indicatorCurrent;
        this.indicatorEnabled = indicatorEnabled;
        this.userAudit = userAudit;
        this.auditDate = auditDate;
    }

    // Getters y Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ThirdParty getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public Neighborhood getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement) {
        this.addressComplement = addressComplement;
    }

    public Boolean getIndicatorCurrent() {
        return indicatorCurrent;
    }

    public void setIndicatorCurrent(Boolean indicatorCurrent) {
        this.indicatorCurrent = indicatorCurrent;
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
}
