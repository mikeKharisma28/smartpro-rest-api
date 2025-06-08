package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.dto.validation.PurchaseOrderDTO;
import com.juaracoding.smartpro_rest_api.service.PurchaseOrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("purchase-order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    // Endpoint untuk mendapatkan daftar purchase orders
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('purchaseOrderList')")
    public ResponseEntity<Object> purchaseOrderList(HttpServletRequest request) {
        return ResponseEntity.ok(purchaseOrderService.findAll(PageRequest.of(0, 10), request));
    }

    // Endpoint untuk membuat Purchase Order baru
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('purchaseOrderCreate')")
    public ResponseEntity<Object> createPurchaseOrder(
            @Valid @RequestBody PurchaseOrderDTO purchaseOrderDTO,
            HttpServletRequest request
    ) {
        return purchaseOrderService.createPurchaseOrder(purchaseOrderDTO, request);
    }
}
