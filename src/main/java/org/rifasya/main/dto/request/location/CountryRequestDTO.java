package org.rifasya.main.dto.request.location;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


public class CountryRequestDTO {
    @NotBlank(message = "El código no puede estar vacío")
    private String code;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

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
}
