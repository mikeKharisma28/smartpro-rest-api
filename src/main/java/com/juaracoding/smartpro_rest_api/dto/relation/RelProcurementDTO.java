package com.juaracoding.smartpro_rest_api.dto.relation;

import java.time.LocalDate;

public class RelProcurementDTO {
    private String procurementNo;

    private String itemName;

    private String itemDescription;

    private Integer requestedQuantity;

    private String unit;

    private LocalDate expectedDate;



    // setter getter
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
}
