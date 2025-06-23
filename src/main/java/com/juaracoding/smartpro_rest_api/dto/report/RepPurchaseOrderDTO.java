package com.juaracoding.smartpro_rest_api.dto.report;

import java.math.BigDecimal;

/***
 * Author: Alfin, 2025-06-19
 */

public class RepPurchaseOrderDTO {

    private String purchaseOrderNo;
    private String unit;
    private Double unitPrice;
    private Integer quantityOrdered;
    private Double totalAmount;
    private String status;

    // Constructor
    public RepPurchaseOrderDTO(String purchaseOrderNo, String unit, BigDecimal unitPrice, Integer quantityOrdered, BigDecimal totalAmount, Short status) {
        this.purchaseOrderNo = this.purchaseOrderNo;
        this.unit = this.unit;
        this.unitPrice = this.unitPrice;
        this.quantityOrdered = this.quantityOrdered;
        this.totalAmount = this.totalAmount;
        this.status = this.status;
    }

    // Getters and Setters
    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
