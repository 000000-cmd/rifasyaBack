package org.rifasya.main.entities.meta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "listregistry")
public class ListRegistry {

    @Id
    @Column(name = "IdRegistry")
    private UUID id;

    @Column(name = "TechnicalName", nullable = false, unique = true)
    private String technicalName;

    @Column(name = "DisplayName", nullable = false)
    private String displayName;

    @Column(name = "ApiEndpoint", nullable = false)
    private String apiEndpoint;

    @Column(name = "Description")
    private String description;

    // --- Constructores ---
    public ListRegistry() {
    }

    public ListRegistry(UUID id, String technicalName, String displayName, String apiEndpoint, String description) {
        this.id = id;
        this.technicalName = technicalName;
        this.displayName = displayName;
        this.apiEndpoint = apiEndpoint;
        this.description = description;
    }

    // --- GETTERS Y SETTERS CORREGIDOS ---

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
