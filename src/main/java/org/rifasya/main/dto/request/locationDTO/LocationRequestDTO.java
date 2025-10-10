package org.rifasya.main.dto.request.locationDTO;

import jakarta.validation.constraints.NotBlank;

public class LocationRequestDTO {
    @NotBlank
    private String neighborhoodCode;
    @NotBlank
    private String address;

    private String addressComplement;

    private Boolean isCurrent = true;

    public LocationRequestDTO() {}

    public LocationRequestDTO(String neighborhoodCode, String address, String addressComplement, Boolean isCurrent) {
        this.neighborhoodCode = neighborhoodCode;
        this.address = address;
        this.addressComplement = addressComplement;
        this.isCurrent = isCurrent;
    }

    public String getNeighborhoodCode() {
        return neighborhoodCode;
    }

    public void setNeighborhoodCode(String neighborhoodCode) {
        this.neighborhoodCode = neighborhoodCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement) {
        this.addressComplement = addressComplement;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean current) {
        isCurrent = current;
    }
}
