package org.rifasya.main.models;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private UUID id;

    @NotBlank
    private String user;

    @NotBlank
    private String password;

    @NotBlank
    private String mail;

    private String cellular;
    private byte[] attachment;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotBlank String getUser() {
        return user;
    }

    public void setUser(@NotBlank String user) {
        this.user = user;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotBlank String getMail() {
        return mail;
    }

    public void setMail(@NotBlank String mail) {
        this.mail = mail;
    }

    public String getCellular() {
        return cellular;
    }

    public void setCellular(String cellular) {
        this.cellular = cellular;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }
}
