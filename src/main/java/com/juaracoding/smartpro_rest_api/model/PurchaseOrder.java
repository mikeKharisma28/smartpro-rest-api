package com.juaracoding.smartpro_rest_api.model;

import jakarta.persistence.*;
import org.apache.poi.hpsf.Decimal;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PurchaseOrder", schema = "TransactionData")
public class PurchaseOrder {

    @Id
    @Column(name = "PurchaseOrderNo", length = 15, nullable = false)
    private String purchaseOrderNo;

    @Column(name = "QuantityOrdered", nullable = false)
    private Integer quantityOrdered;

    @Column(name = "UnitPrice", nullable = false)
    private Decimal unitPrice;

    @Column(name = "Unit", length = 10)
    private String unit;

    @Column(name = "TotalAmount", nullable = false)
    private Decimal totalAmount;

    @Column(name = "Status", nullable = false)
    private Integer status;

    @Column(name = "CreatedBy", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CreatedDate", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "UpdatedBy", insertable = false)
    private Long updatedBy;

    @Column(name = "CreatedDate", insertable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    // columns that are foreign key
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PurchaseRequestNo", nullable = false)
    private PurchaseRequest purchaseRequest;

    // setters getters
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

    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }
}
