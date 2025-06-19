package com.juaracoding.smartpro_rest_api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juaracoding.smartpro_rest_api.dto.relation.RelMenuDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ResRoleDTO {

    private Long id;

    private String name;

    private LocalDateTime updatedDate;

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

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
