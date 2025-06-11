package com.juaracoding.smartpro_rest_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MenuLoginDTO {
    private String name;

    private String path;

    @JsonIgnore
    private String parentMenuName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentMenuName() {
        return parentMenuName;
    }

    public void setParentMenuName(String parentMenuName) {
        this.parentMenuName = parentMenuName;
    }
}
