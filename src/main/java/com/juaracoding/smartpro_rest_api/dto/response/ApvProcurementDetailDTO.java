package com.juaracoding.smartpro_rest_api.dto.response;

import java.time.LocalDate;

public class ApvProcurementDetailDTO {
    private String procurementNo;

    private String itemName;

    private String itemDescription;

    private Integer requestedQuantity;

    private String unit;

    private LocalDate expectedDate;

    private Integer status;
}
