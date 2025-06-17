package com.juaracoding.smartpro_rest_api.dto.relation;

import jakarta.validation.constraints.NotNull;

public class RelDivisionDTO {
    private Long id;

    private String name;

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
}
