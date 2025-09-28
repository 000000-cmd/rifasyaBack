package org.rifasya.main.dto.request.UserDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedUserRequestDTO {
    @NotBlank
    private String user;

    @NotBlank
    private String password;

    @NotBlank
    private String mail;

    private String cellular;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCellular() {
        return cellular;
    }

    public void setCellular(String cellular) {
        this.cellular = cellular;
    }
}
