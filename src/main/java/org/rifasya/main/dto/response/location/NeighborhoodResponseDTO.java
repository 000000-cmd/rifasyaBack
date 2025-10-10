package org.rifasya.main.dto.response.location;

import lombok.Data;

import java.util.UUID;


public class NeighborhoodResponseDTO {
    private UUID id;
    private String code;
    private String name;

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
}
