package org.rifasya.main.dto.response;

import java.util.UUID;

public class ListRegistryResponseDTO {
    private UUID id;
    private String technicalName;
    private String displayName;
    private String apiEndpoint;
    private String description;

    // --- Constructores, Getters y Setters ---
    public ListRegistryResponseDTO() {}

    public ListRegistryResponseDTO(UUID id, String technicalName, String displayName, String apiEndpoint, String description) {
        this.id = id;
        this.technicalName = technicalName;
        this.displayName = displayName;
        this.apiEndpoint = apiEndpoint;
        this.description = description;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTechnicalName() { return technicalName; }
    public void setTechnicalName(String technicalName) { this.technicalName = technicalName; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getApiEndpoint() { return apiEndpoint; }
    public void setApiEndpoint(String apiEndpoint) { this.apiEndpoint = apiEndpoint; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}