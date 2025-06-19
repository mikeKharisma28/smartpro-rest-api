package com.juaracoding.smartpro_rest_api.dto.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juaracoding.smartpro_rest_api.dto.relation.RelMenuDTO;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class RoleDTO {

    @NotNull
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s\\.]{4,70}$",
            message = "Invalid format! Only alphabets, dots and spaces are allowed, min 4 to 70 characters")
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
