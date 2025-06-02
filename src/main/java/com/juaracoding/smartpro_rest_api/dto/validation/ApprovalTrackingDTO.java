package com.juaracoding.smartpro_rest_api.dto.validation;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ApprovalTrackingDTO {

    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9\\-]{0,15}$",
            message = "Invalid format! Allowed format: uppercase letters, numbers and hypen only, length allowed 0-15 characters, for example: PRC-12345")
    private String procurementRequest;

    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9\\-]{0,15}$",
            message = "Invalid format! Allowed format: uppercase letters, numbers and hypen only, length allowed 0-15 characters, for example: PRC-12345")
    private String purchaseRequest;

    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9\\-]{0,15}$",
            message = "Invalid format! Allowed format: uppercase letters, numbers and hypen only, length allowed 0-15 characters, for example: PRC-12345")
    private String purchaseOrderNo;

    public String getProcurementRequest() {
        return procurementRequest;
    }

    public void setProcurementRequest(String procurementRequest) {
        this.procurementRequest = procurementRequest;
    }

    public String getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(String purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }
}
