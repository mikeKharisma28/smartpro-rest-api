package com.juaracoding.smartpro_rest_api.dto.validation;

import jakarta.validation.constraints.Pattern;
import org.apache.poi.hpsf.Decimal;

public class PurchaseOrderDTO {

    @Pattern(regexp = "^(1[0-4]\\d|150|[1-9]\\d?|)$", message = "Only numeric are allowed, range of 1 to 150")
    private Integer quantityOrdered;

    @Pattern(regexp = "^(10000(\\.\\d+)?|[1-9]\\d{4,7}(\\.\\d+)?)$\n", message = "Only numeric are allowed, range of 10000 to 100000000")
    private Decimal unitPrice;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,10}$", message = "Only alphabets are allowed and length from 3 to 10 characters")
    private String unit;

    @Pattern(regexp = "^(10000(\\.\\d+)?|[1-9]\\d{4,7}(\\.\\d+)?)$\n", message = "Only numeric are allowed, range of 10000 to 100000000")
    private Decimal totalAmount;

    @Pattern(regexp = "^[0-3]{1}$", message = "Only numeric are allowed, range of 0 to 3")
    private Integer status;

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public Decimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Decimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Decimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Decimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
