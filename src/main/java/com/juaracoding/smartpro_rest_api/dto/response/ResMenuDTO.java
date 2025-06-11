package com.juaracoding.smartpro_rest_api.dto.response;

import com.juaracoding.smartpro_rest_api.model.Menu;

public class ResMenuDTO {

    private Long id;

    private String name;

    private String path;

    private ResDivisionDTO division;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public ResDivisionDTO getDivision() {
        return division;
    }

    public void setDivision(ResDivisionDTO division) {
        this.division = division;
    }
}
