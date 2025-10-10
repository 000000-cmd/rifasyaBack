package org.rifasya.main.dto.response.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;


public class DepartmentResponseDTO {
    private UUID id;
    private String code;
    private String name;
    private List<MunicipalityResponseDTO> municipalities;

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

    public List<MunicipalityResponseDTO> getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(List<MunicipalityResponseDTO> municipalities) {
        this.municipalities = municipalities;
    }
}