package org.rifasya.main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "IdUser")
    private UUID id;

    @Column(name = "`User`", nullable = false, unique = true)
    private String user;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Cellular", unique = true)
    private String cellular;

    @Column(name = "Mail", unique = true)
    private String mail;

    @Column(name = "IndicatorEnabled", nullable = false)
    private Boolean indicatorEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserAudit")
    @JsonIgnoreProperties({"userAudit"}) // evita bucles infinitos al serializar
    private User userAudit;

    @Column(name = "AuditDate", nullable = false)
    private LocalDateTime auditDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdAttachment")
    private Attachment Attachment;

    public User() {
    }

    // Constructor personalizado para creación de usuario
    public User(UUID id, String user, String password, String cellular, String mail,
                Boolean indicatorEnabled, User userAudit, LocalDateTime auditDate, Attachment Attachment) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.cellular = cellular;
        this.mail = mail;
        this.indicatorEnabled = indicatorEnabled;
        this.userAudit = userAudit;
        this.auditDate = auditDate;
        this.Attachment = Attachment;
    }

    @PrePersist
    protected void onCreate() {
        this.auditDate = LocalDateTime.now();
        this.indicatorEnabled = true;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellular() {
        return cellular;
    }

    public void setCellular(String cellular) {
        this.cellular = cellular;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public Attachment getAttachment() {
        return Attachment;
    }

    public void setAttachment(Attachment idAttachment) {
        this.Attachment = idAttachment;
    }
}