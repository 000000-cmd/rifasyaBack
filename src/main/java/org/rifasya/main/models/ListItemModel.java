package org.rifasya.main.models;

/**
 * Modelo simple para transportar datos de ítems de lista al frontend.
 * Usado comúnmente para llenar selects o dropdowns.
 */
public class ListItemModel {
    private String code;
    private Integer order;
    private String name;

    // --- Constructores ---
    public ListItemModel() {}

    public ListItemModel(String code, Integer order, String name) {
        this.code = code;
        this.order = order;
        this.name = name;
    }

    // --- Getters y Setters ---
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
