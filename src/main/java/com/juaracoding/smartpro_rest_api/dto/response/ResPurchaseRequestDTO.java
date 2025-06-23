package com.juaracoding.smartpro_rest_api.dto.response;

import com.juaracoding.smartpro_rest_api.dto.relation.RelProcurementDTO;

import java.math.BigDecimal;

/***
 * Author: Michael, 2025-06-21
 */

public class ResPurchaseRequestDTO {

    private BigDecimal estimatedPrice;

    private Integer estimatedQuantity;

    private String unit;

    private String linkSpecificationUrl;

    private String linkReferenceUrl;

    private Short status;

    private RelProcurementDTO procurementRequest;

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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public RelProcurementDTO getProcurementRequest() {
        return procurementRequest;
    }

    public void setProcurementRequest(RelProcurementDTO procurementRequest) {
        this.procurementRequest = procurementRequest;
    }
}
