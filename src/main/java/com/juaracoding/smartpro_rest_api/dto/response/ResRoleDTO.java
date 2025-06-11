package com.juaracoding.smartpro_rest_api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juaracoding.smartpro_rest_api.dto.relation.RelMenuDTO;

import java.util.List;

public class ResRoleDTO {

    private Long id;

    private String name;

    @JsonProperty("list-menu")
    private List<RelMenuDTO> listMenu;

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

    public List<RelMenuDTO> getListMenu() {
        return listMenu;
    }

    public void setListMenu(List<RelMenuDTO> listMenu) {
        this.listMenu = listMenu;
    }
}
