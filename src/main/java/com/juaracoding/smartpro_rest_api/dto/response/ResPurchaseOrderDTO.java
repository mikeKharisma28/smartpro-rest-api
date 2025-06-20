package com.juaracoding.smartpro_rest_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ResPurchaseOrderDTO {

    private String purchaseOrderNo;
    private Integer quantityOrdered;
    private BigDecimal unitPrice;
    private String unit;
    private BigDecimal totalAmount;
    private Short status;
    private Long createdBy;
    private LocalDateTime createdDate;
    private Long updatedBy;
    private LocalDateTime updatedDate;
    private String purchaseRequestNo;

    // Constructor
    public ResPurchaseOrderDTO(String purchaseOrderNo, Integer quantityOrdered, BigDecimal unitPrice,
                               String unit, BigDecimal totalAmount, Short status, Long createdBy,
                               LocalDateTime createdDate, Long updatedBy, LocalDateTime updatedDate,
                               String purchaseRequestNo) {
        this.purchaseOrderNo = purchaseOrderNo;
        this.quantityOrdered = quantityOrdered;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.purchaseRequestNo = purchaseRequestNo;
    }

    // Getters and Setters
    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getPurchaseRequestNo() {
        return purchaseRequestNo;
    }

    public void setPurchaseRequestNo(String purchaseRequestNo) {
        this.purchaseRequestNo = purchaseRequestNo;
    }

    // toString method for easy representation of the object
    @Override
    public String toString() {
        return "ResPurchaseOrderDTO{" +
                "purchaseOrderNo='" + purchaseOrderNo + '\'' +
                ", quantityOrdered=" + quantityOrdered +
                ", unitPrice=" + unitPrice +
                ", unit='" + unit + '\'' +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", updatedBy=" + updatedBy +
                ", updatedDate=" + updatedDate +
                ", purchaseRequestNo='" + purchaseRequestNo + '\'' +
                '}';
    }

    // equals method for comparing objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResPurchaseOrderDTO that = (ResPurchaseOrderDTO) o;
        return Objects.equals(purchaseOrderNo, that.purchaseOrderNo) &&
                Objects.equals(quantityOrdered, that.quantityOrdered) &&
                Objects.equals(unitPrice, that.unitPrice) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(updatedBy, that.updatedBy) &&
                Objects.equals(updatedDate, that.updatedDate) &&
                Objects.equals(purchaseRequestNo, that.purchaseRequestNo);
    }

    // hashCode method for hashing the object
    @Override
    public int hashCode() {
        return Objects.hash(purchaseOrderNo, quantityOrdered, unitPrice, unit, totalAmount,
                status, createdBy, createdDate, updatedBy, updatedDate, purchaseRequestNo);
    }
}
