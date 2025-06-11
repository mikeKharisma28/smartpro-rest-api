package com.juaracoding.smartpro_rest_api.dto.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juaracoding.smartpro_rest_api.dto.relation.RelDivisionDTO;
import com.juaracoding.smartpro_rest_api.model.Menu;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MenuDTO {

    @NotNull(message = "Name Must Not Be Null")
    @Pattern(regexp = "^([a-zA-Z0-9\\.]{8,16})$",
            message = "Invalid format! Allowed format: Uppercase & lowercase letters, numbers and dots only, length allowed 8-16 characters, for example: Michael.Laksa123")
    private String name;

    @NotNull(message = "Path Must Not Be Null")
    @Pattern(regexp = "^[a-z\\/\\-]{5,50}$",message = "Path Invalid lowercase, hyphen and slash Min 5 Max 50 , ex : /group-menu")
    private String path;

    @NotNull(message = "Relation Cannot Be Empty")
    @JsonProperty("division")
    private RelDivisionDTO division;

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

    public RelDivisionDTO getDivision() {
        return division;
    }

    public void setDivision(RelDivisionDTO division) {
        this.division = division;
    }
}
