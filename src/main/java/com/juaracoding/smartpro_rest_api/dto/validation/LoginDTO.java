package com.juaracoding.smartpro_rest_api.dto.validation;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 {
     "email":"mike@gmail.com",
     "password": "P@ssw0rd123"
 }
 */
public class LoginDTO {
    @NotNull(message = "Username tidak boleh kosong")
    @NotEmpty(message = "Username tidak boleh kosong")
    @NotBlank(message = "Username tidak boleh kosong")
    @Pattern(regexp = "^([a-z0-9\\.]{8,16})$",
            message = "Invalid format! Allowed format: lowercase letters, numbers and dots only, length allowed 8-16 characters, for example: michael.laksa123")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@_#\\-$])[\\w].{8,15}$",
            message = "Minimum format allowed: 1 number, 1 lowercase letter, 1 uppercase letter, 1 special characters (_ \"Underscore\", - \"Hyphen\", # \"Hash\", or $ \"Dollar\" or @ \"At\"). Password length allowed 9-16 characters alphanumeric combinations, example: P@ssw0rd123")
    private String password;

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
}
