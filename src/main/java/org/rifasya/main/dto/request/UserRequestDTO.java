package org.rifasya.main.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.rifasya.main.entities.User;

import java.util.UUID;

public class UserRequestDTO {

    private String user;
    private String password;
    private String cellular;
    private String mail;
    private UUID userAuditId;
    private byte[] Attachment;

    // getters y setters manuales
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getCellular() { return cellular; }
    public void setCellular(String cellular) { this.cellular = cellular; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public UUID getUserAuditId() { return userAuditId; }
    public void setUserAuditId(UUID userAuditId) { this.userAuditId = userAuditId; }
    public byte[] getAttachment() {return Attachment;}
    public void setAttachment(byte[] attachment) {Attachment = attachment;}
}