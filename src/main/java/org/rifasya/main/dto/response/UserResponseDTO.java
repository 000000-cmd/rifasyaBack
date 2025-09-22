package org.rifasya.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.rifasya.main.entities.Attachment;

import java.time.LocalDateTime;
import java.util.UUID;



public class UserResponseDTO {

    private UUID id;
    private String user;
    private String cellular;
    private String mail;
    private Boolean indicatorEnabled;
    private LocalDateTime auditDate;
    private Attachment Attachment;

    public UserResponseDTO(UUID id,
                           String user,
                           String cellular,
                           String mail,
                           Boolean indicatorEnabled,
                           LocalDateTime auditDate,
                           Attachment attachment) {
        this.id = id;
        this.user = user;
        this.cellular = cellular;
        this.mail = mail;
        this.indicatorEnabled = indicatorEnabled;
        this.auditDate = auditDate;
        Attachment = attachment;
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

    public LocalDateTime getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(LocalDateTime auditDate) {
        this.auditDate = auditDate;
    }

    public Attachment getAttachment() {return Attachment;}

    public void setAttachment(Attachment attachment) {Attachment = attachment;}
}
