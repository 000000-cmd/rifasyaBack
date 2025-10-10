package org.rifasya.main.dto.response.location;

public class LocationResponseDTO {
    private String country;
    private String department;
    private String municipality;
    private String neighborhood;
    private String address;
    private String addressComplement;

    public LocationResponseDTO() {
    }

    public LocationResponseDTO(String country, String department, String municipality, String neighborhood, String address, String addressComplement) {
        this.country = country;
        this.department = department;
        this.municipality = municipality;
        this.neighborhood = neighborhood;
        this.address = address;
        this.addressComplement = addressComplement;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
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
}
