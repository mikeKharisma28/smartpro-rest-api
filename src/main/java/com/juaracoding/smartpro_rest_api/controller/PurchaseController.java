package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.dto.validation.PurchaseRequestDTO;
import com.juaracoding.smartpro_rest_api.service.ProcurementService;
import com.juaracoding.smartpro_rest_api.service.PurchaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("purchase")
public class PurchaseController {

    @Autowired
    private ProcurementService procurementService;

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/request")
    @PreAuthorize("hasAuthority('purchaseRequest')")
    public RequestEntity<Object> requestList(HttpServletRequest request) {
        return null;
    }

    @PostMapping("/request")
    @PreAuthorize("hasAuthority('PurchaseRequest')")
    public RequestEntity<Object> requestPurchase(
            @Valid @RequestBody PurchaseRequestDTO purchaseRequestDTO,
            HttpServletRequest request
    ) {
        return null;
    }
}
