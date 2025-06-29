package com.juaracoding.smartpro_rest_api.dto.validation;

import com.juaracoding.smartpro_rest_api.dto.relation.RelProcurementDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

/***
 * Author: Michael, 2025-05-20
 * Edited by: Reynaldi, 2025-06-03
 * Bugs fixed:
 * - Added Setters and Getters
 */

public class PurchaseRequestDTO {

//    @Pattern(regexp = "^(10000(\\.\\d+)?|[1-9]\\d{4,7}(\\.\\d+)?)$\n", message = "Only numeric are allowed, range of 10000 to 100000000")
    @Range(min = 10000, max = 100000000, message = "Only numeric are allowed, range of 10000 to 100000000")
    private BigDecimal estimatedPrice;

//    @Pattern(regexp = "^(1[0-4]\\d|150|[1-9]\\d?|)$", message = "Only numeric are allowed, range of 1 to 150")
    @Range(min = 1, max = 150, message = "Only numeric are allowed, range of 1 to 150")
    private Integer estimatedQuantity;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,10}$", message = "Only alphabets are allowed and length from 3 to 10 characters")
    private String unit;

    @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\-]+\\.)+[\\w]{2,}(\\/\\S*)?$\n", message = "Format URL invalid!")
    private String linkSpecificationUrl;

    @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\-]+\\.)+[\\w]{2,}(\\/\\S*)?$\n", message = "Format URL invalid!")
    private String linkReferenceUrl;

    @Range(max = 3, message = "Only numeric are allowed, range of 0 to 3")
    private Short status;

    @NotNull(message = "Procurement request must not be null!")
    private RelProcurementDTO procurementRequest;
  
    // setters getters
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
