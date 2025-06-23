package com.juaracoding.smartpro_rest_api.dto.validation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.juaracoding.smartpro_rest_api.dto.relation.RelDivisionDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

public class ValidProcurementRequestDTO {

    @NotNull(message = "This field cannot be null!")
    @Pattern(regexp = "^[a-zA-Z\\s]{5,50}$",message = "Format Invalid! Length allowed from 5 to 50 characters")
    @JsonProperty("item-name")
    private String itemName;

    @NotNull(message = "This field cannot be null!")
    @Pattern(regexp = "^[a-zA-Z\\s]{15,255}$",message = "Only alphabets are allowed and length from 15 to 255 characters")
    @JsonProperty("item-description")
    private String itemDescription;

    @Range(min = 1, max = 150, message = "Only numeric are allowed, range of 1 to 150")
    @JsonProperty("requested-quatity")
    private Integer requestedQuantity;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,10}$",message = "Only alphabets are allowed and length from 3 to 10 characters")
    private String unit;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("expected-date")
    private LocalDate expectedDate;

    @Range(max = 3, message = "Only numeric are allowed, range of 0 to 3")
    private Integer status;

    @NotNull(message = "Division can not be null")
    private RelDivisionDTO division;

    // setters getters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public RelDivisionDTO getDivision() {
        return division;
    }

    public void setDivision(RelDivisionDTO division) {
        this.division = division;
    }
}
