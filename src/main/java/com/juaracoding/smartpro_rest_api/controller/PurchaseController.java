package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.service.ProcurementService;
import com.juaracoding.smartpro_rest_api.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("purchase")
public class PurchaseController {

    @Autowired
    private ProcurementService procurementService;

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/request")
    @PreAuthorize("hasAuthority('purchaseRequest')")
    public RequestEntity<Object> requestList() {
        return null;
    }
}
