package com.juaracoding.smartpro_rest_api.dto.validation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.poi.hpsf.Decimal;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class PurchaseRequestDTO {

    @NotNull(message = "This field cannot be null!")
    @Pattern(regexp = "^(10000(\\.\\d+)?|[1-9]\\d{4,7}(\\.\\d+)?)$\n", message = "Only numeric are allowed, range of 10000 to 100000000")
    private Decimal estimatedPrice;

    @NotNull(message = "This field cannot be null!")
    @Pattern(regexp = "^(1[0-4]\\d|150|[1-9]\\d?|)$", message = "Only numeric are allowed, range of 1 to 150")
    private Integer estimatedQuantity;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,10}$", message = "Only alphabets are allowed and length from 3 to 10 characters")
    private String unit;

    @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\-]+\\.)+[\\w]{2,}(\\/\\S*)?$\n", message = "Format URL invalid!")
    private String linkSpecificationUrl;

    @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\-]+\\.)+[\\w]{2,}(\\/\\S*)?$\n", message = "Format URL invalid!")
    private String linkReferenceUrl;

    private Integer status = 0;

    // setters getters
    public Decimal getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(Decimal estimatedPrice) {
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
}
