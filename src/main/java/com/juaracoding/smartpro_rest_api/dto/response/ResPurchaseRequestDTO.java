package com.juaracoding.smartpro_rest_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class ResPurchaseRequestDTO{

        private String purchaseRequestNo;
        private BigDecimal estimatedPrice;
        private Integer estimatedQuantity;
        private String unit;
        private String linkSpecificationUrl;
        private String linkReferenceUrl;
        private Integer status;
        private Long createdBy;
        private LocalDateTime createdDate;
        private Long updatedBy;
        private LocalDateTime updatedDate;
        private Long procurementRequestNo;

        // Constructor
        public ResPurchaseRequestDTO(String purchaseRequestNo, BigDecimal estimatedPrice, Integer estimatedQuantity,
                                     String unit, String linkSpecificationUrl, String linkReferenceUrl,
                                     Integer status, Long createdBy, LocalDateTime createdDate,
                                     Long updatedBy, LocalDateTime updatedDate, Long procurementRequestNo) {
            this.purchaseRequestNo = purchaseRequestNo;
            this.estimatedPrice = estimatedPrice;
            this.estimatedQuantity = estimatedQuantity;
            this.unit = unit;
            this.linkSpecificationUrl = linkSpecificationUrl;
            this.linkReferenceUrl = linkReferenceUrl;
            this.status = status;
            this.createdBy = createdBy;
            this.createdDate = createdDate;
            this.updatedBy = updatedBy;
            this.updatedDate = updatedDate;
            this.procurementRequestNo = procurementRequestNo;
        }

        // Getters and Setters
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

        public String getLinkSpecificationUrl() {
            return linkSpecificationUrl;
        }

        public void setLinkSpecificationUrl(String linkSpecificationUrl) {
            this.linkSpecificationUrl = linkSpecificationUrl;
        }

        public String getLinkReferenceUrl() {
            return linkReferenceUrl;
        }

        public void setLinkReferenceUrl(String linkReferenceUrl) {
            this.linkReferenceUrl = linkReferenceUrl;
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

        public Long getProcurementRequestNo() {
            return procurementRequestNo;
        }

        public void setProcurementRequestNo(Long procurementRequestNo) {
            this.procurementRequestNo = procurementRequestNo;
        }
    }