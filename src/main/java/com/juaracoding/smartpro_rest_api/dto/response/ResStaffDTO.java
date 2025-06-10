package com.juaracoding.smartpro_rest_api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResStaffDTO {

    private Long id;

    @JsonProperty("full-name")
    private String fullName;

    @JsonProperty("phone-number")
    private String phoneNumber;

    private Long divisionId;

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

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }
}
