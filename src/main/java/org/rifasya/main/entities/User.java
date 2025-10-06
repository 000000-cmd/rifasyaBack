package org.rifasya.main.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users") // Asumo el nombre de la tabla "users"
public class User {

    @Id
    @Column(name = "IdUser") // Corregido
    private UUID id;

    @Column(name = "Username", nullable = false, unique = true)
    private String user;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "Cellular")
    private String cellular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUserAudit", referencedColumnName = "IdUser") // Corregido
    private User userAudit;

    @Column(name = "AuditDate", nullable = false)
    private LocalDateTime auditDate = LocalDateTime.now();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Attachment attachment;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<UserRole> roles;

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public String getCellular() { return cellular; }
    public void setCellular(String cellular) { this.cellular = cellular; }
    public User getUserAudit() { return userAudit; }
    public void setUserAudit(User userAudit) { this.userAudit = userAudit; }
    public LocalDateTime getAuditDate() { return auditDate; }
    public void setAuditDate(LocalDateTime auditDate) { this.auditDate = auditDate; }
    public Attachment getAttachment() { return attachment; }
    public void setAttachment(Attachment attachment) { this.attachment = attachment; }
    public Set<UserRole> getRoles() { return roles; }
    public void setRoles(Set<UserRole> roles) { this.roles = roles; }
}