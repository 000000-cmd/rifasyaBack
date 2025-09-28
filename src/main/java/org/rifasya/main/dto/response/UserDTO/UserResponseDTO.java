package org.rifasya.main.dto.response.UserDTO;

import org.rifasya.main.entities.Attachment;

import java.time.LocalDateTime;
import java.util.UUID;



public class UserResponseDTO {

    private UUID id;
    private String user;
    private String cellular;
    private String mail;

    public UserResponseDTO(UUID id,
                           String user,
                           String cellular,
                           String mail) {
        this.id = id;
        this.user = user;
        this.cellular = cellular;
        this.mail = mail;
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
}
