package org.rifasya.main.dto.request.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;


public class MunicipalityRequestDTO {
    @NotBlank(message = "El código no puede estar vacío")
    private String code;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotNull(message = "El ID del departamento es obligatorio")
    private UUID departmentId;

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

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }
}
