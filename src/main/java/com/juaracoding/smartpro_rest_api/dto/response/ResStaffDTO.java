package com.juaracoding.smartpro_rest_api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juaracoding.smartpro_rest_api.dto.relation.RelDivisionDTO;
import com.juaracoding.smartpro_rest_api.dto.relation.RelRoleDTO;

/***
 * Author : Reynaldi
 * Created date : 2025-06-10
 * Edited by : Michael
 * Edited date : 2025-06-17
 */

public class ResStaffDTO {

    private Long id;

    @JsonProperty("full-name")
    private String fullName;

    @JsonProperty("phone-number")
    private String phoneNumber;

    private RelRoleDTO role;

    private RelDivisionDTO division;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RelRoleDTO getRole() {
        return role;
    }

    public void setRole(RelRoleDTO role) {
        this.role = role;
    }

    public RelDivisionDTO getDivision() {
        return division;
    }

    public void setDivision(RelDivisionDTO division) {
        this.division = division;
    }
}
