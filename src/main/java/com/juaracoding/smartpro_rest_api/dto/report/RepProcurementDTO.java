package com.juaracoding.smartpro_rest_api.dto.report;

import java.time.LocalDate;

/***
 * Author: Michael, 2025-05-20
 * Last edited by: Reynaldi, 2025-05-28
 * Added:
 * - Getters and Setters
 * - Class renamed
 */

public class RepProcurementDTO {

    private String procurementNo;

    private String itemName;

    private String itemDescription;

    private Integer requestedQuantity;

    private String unit;

    private LocalDate expectedDate;

    private Short status;

    public String getProcurementNo() {
        return procurementNo;
    }

    public void setProcurementNo(String procurementNo) {
        this.procurementNo = procurementNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}
