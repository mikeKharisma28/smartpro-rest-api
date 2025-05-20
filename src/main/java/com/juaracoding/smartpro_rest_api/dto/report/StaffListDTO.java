package com.juaracoding.smartpro_rest_api.dto.report;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class StaffListDTO {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private Long divisionId;
}
