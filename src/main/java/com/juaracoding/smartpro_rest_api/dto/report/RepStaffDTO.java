package com.juaracoding.smartpro_rest_api.dto.report;

/***
 * Author: Michael, 2025-05-20
 * Last edited by: Reynaldi, 2025-05-28
 * Added:
 * - Getters and Setters
 * - Class renamed
 */

public class RepStaffDTO {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
