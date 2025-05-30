package com.juaracoding.smartpro_rest_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PurchaseRequest", schema = "TransactionData")
public class PurchaseRequest {

    @Id
    @Column(name = "PurchaseRequestNo", length = 15, nullable = false)
    private String purchaseRequestNo;

    @Column(name = "EstimatedPrice", nullable = false)
    private BigDecimal estimatedPrice;

    @Column(name = "EstimatedQuantity", nullable = false)
    private Integer estimatedQuantity;

    @Column(name = "Unit", length = 10)
    private String unit;

    @Column(name = "ItemSpecificationUrl")
    private LocalDate itemSpecificationUrl;

    @Column(name = "ItemReferenceUrl")
    private LocalDate itemReferenceUrl;

    @Column(name = "Status", nullable = false)
    private Integer status;

    @Column(name = "CreatedBy", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CreatedDate", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "UpdatedBy", insertable = false)
    private Long updatedBy;

    @Column(name = "UpdatedDate", insertable = false)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    // columns that are foreign key
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProcurementRequestNo", nullable = false)
    private ProcurementRequest procurementRequest;

    // setters getters
    public String getPurchaseRequestNo() {
        return purchaseRequestNo;
    }

    public void setPurchaseRequestNo(String purchaseRequestNo) {
        this.purchaseRequestNo = purchaseRequestNo;
    }

    public BigDecimal getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(BigDecimal estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public Integer getEstimatedQuantity() {
        return estimatedQuantity;
    }

    public void setEstimatedQuantity(Integer estimatedQuantity) {
        this.estimatedQuantity = estimatedQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getItemSpecificationUrl() {
        return itemSpecificationUrl;
    }

    public void setItemSpecificationUrl(LocalDate itemSpecificationUrl) {
        this.itemSpecificationUrl = itemSpecificationUrl;
    }

    public LocalDate getItemReferenceUrl() {
        return itemReferenceUrl;
    }

    public void setItemReferenceUrl(LocalDate itemReferenceUrl) {
        this.itemReferenceUrl = itemReferenceUrl;
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

    public ProcurementRequest getProcurementRequest() {
        return procurementRequest;
    }

    public void setProcurementRequest(ProcurementRequest procurementRequest) {
        this.procurementRequest = procurementRequest;
    }
}
