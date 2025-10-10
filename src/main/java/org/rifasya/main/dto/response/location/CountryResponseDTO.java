package org.rifasya.main.dto.response.location;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;


public class CountryResponseDTO {
    private UUID id;
    private String code;
    private String name;
    private List<DepartmentResponseDTO> departments;

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

    public List<DepartmentResponseDTO> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentResponseDTO> departments) {
        this.departments = departments;
    }
}
