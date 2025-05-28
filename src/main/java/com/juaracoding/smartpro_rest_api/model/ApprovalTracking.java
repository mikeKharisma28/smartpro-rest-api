package com.juaracoding.smartpro_rest_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ApprovalTracking", schema = "TransactionData")
public class ApprovalTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Notes")
    private String notes;

    @Column(name = "Timestamp")
    private LocalDateTime timestamp;

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

    // columns that are foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProcurementNo", nullable = false)
    private ProcurementRequest procurementRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PurchaseRequestNo", nullable = false)
    private PurchaseRequest purchaseRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StaffId", nullable = false)
    private Staff staff;

    // setters getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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

    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
