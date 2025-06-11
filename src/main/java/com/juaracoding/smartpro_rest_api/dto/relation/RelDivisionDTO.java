package com.juaracoding.smartpro_rest_api.dto.relation;

import jakarta.validation.constraints.NotNull;

public class RelDivisionDTO {

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
