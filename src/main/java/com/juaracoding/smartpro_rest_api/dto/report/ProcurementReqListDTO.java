package com.juaracoding.smartpro_rest_api.dto.report;

import java.time.LocalDate;

public class ProcurementReqListDTO {
    private String procurementNo;

    private String itemName;

    private String itemDescription;

    private Integer requestedQuantity;

    private String unit;

    private LocalDate expectedDate;

    private Integer status;
}
