package com.juaracoding.smartpro_rest_api.dto.report;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.time.LocalDate;

/***
 * Diambil dan dimasukkan ke dalam dropdown list
 */
public class ApvProcurementReqListDTO {

    private String procurementNo;

    public String getProcurementNo() {
        return procurementNo;
    }

    public void setProcurementNo(String procurementNo) {
        this.procurementNo = procurementNo;
    }
}
