package com.juaracoding.smartpro_rest_api.dto.validation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccessPermissionDTO {

    @NotNull
    private Long id;

    @Pattern(regexp = "^([a-z0-9\\.]{8,16})$",
            message = "Invalid format! Allowed format: lowercase letters, numbers and dots only, length allowed 8-16 characters, for example: michael.laksa123")
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
