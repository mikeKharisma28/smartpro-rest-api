package com.juaracoding.smartpro_rest_api.dto.validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class EditStaffDTO {
    @NotNull(message = "Full name tidak boleh kosong")
    @NotEmpty(message = "Full name tidak boleh kosong")
    @NotBlank(message = "Full name tidak boleh kosong")
    @JsonProperty("full-name")
    private String fullName;

    @Pattern(regexp = "^(62|\\+62|0)8[0-9]{9,13}$",
            message = "Invalid phone number format! Allowed format: min 9 max 13 digits after number 8, for example: (0/62/+62)81111111")
    @JsonProperty("phone-number")
    private String phoneNumber;

    @Pattern(regexp = "^([a-z0-9\\.]{8,16})$",
            message = "Invalid format! Allowed format: lowercase letters, numbers and dots only, length allowed 8-16 characters, for example: michael.laksa123")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$",
            message = "Minimum format allowed: 1 number, 1 lowercase letter, 1 uppercase letter, 1 special characters (_ \"Underscore\", - \"Hyphen\", # \"Hash\", or $ \"Dollar\" or @ \"At\"). Password length allowed 9-16 characters alphanumeric combinations, example: P@ssw0rd123")
    private String password;

    @NotNull(message = "Division ID tidak boleh kosong")
    @NotEmpty(message = "Division ID tidak boleh kosong")
    @NotBlank(message = "Division ID tidak boleh kosong")
    private Long divisionId;

    // setters getters
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Long divisionId) {
        this.divisionId = divisionId;
    }
}
