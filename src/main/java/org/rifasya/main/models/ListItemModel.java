package org.rifasya.main.models;

public class ListItemModel {
    private String code;
    private Integer order;
    private String name;

    public ListItemModel(String code, Integer order, String name) {
        this.code = code;
        this.order = order;
        this.name = name;
    }

    public ListItemModel() {}

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
