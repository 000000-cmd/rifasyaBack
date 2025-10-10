package org.rifasya.main.dto.response.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;


public class MunicipalityResponseDTO {
    private UUID id;
    private String code;
    private String name;
    private List<NeighborhoodResponseDTO> neighborhoods;

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

    public List<NeighborhoodResponseDTO> getNeighborhoods() {
        return neighborhoods;
    }

    public void setNeighborhoods(List<NeighborhoodResponseDTO> neighborhoods) {
        this.neighborhoods = neighborhoods;
    }
}
