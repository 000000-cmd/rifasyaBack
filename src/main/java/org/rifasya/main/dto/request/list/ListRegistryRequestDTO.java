package org.rifasya.main.dto.request.list;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ListRegistryRequestDTO {

    @NotBlank(message = "El nombre técnico es obligatorio")
    @Size(max = 100)
    private String technicalName;

    @NotBlank(message = "El nombre a mostrar es obligatorio")
    @Size(max = 150)
    private String displayName;

    @NotBlank(message = "El endpoint del API es obligatorio")
    @Size(max = 200)
    private String apiEndpoint;

    @Size(max = 500)
    private String description;

    // --- Getters y Setters ---
    public String getTechnicalName() { return technicalName; }
    public void setTechnicalName(String technicalName) { this.technicalName = technicalName; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getApiEndpoint() { return apiEndpoint; }
    public void setApiEndpoint(String apiEndpoint) { this.apiEndpoint = apiEndpoint; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}