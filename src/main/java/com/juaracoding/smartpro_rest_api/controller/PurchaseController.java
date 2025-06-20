package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.dto.validation.PurchaseRequestDTO;
import com.juaracoding.smartpro_rest_api.model.PurchaseRequest;
import com.juaracoding.smartpro_rest_api.service.PurchaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    // Endpoint untuk mendapatkan daftar request
    @GetMapping("/request")
//    @PreAuthorize("hasAuthority('PurchaseRequest')")
    public ResponseEntity<Object> requestList(HttpServletRequest request) {
        return ResponseEntity.ok(purchaseService.findAll(PageRequest.of(0, 10), request));
    }

    // Endpoint untuk request purchase baru
    @PostMapping("/request")
//    @PreAuthorize("hasAuthority('PurchaseRequest')")
    public ResponseEntity<Object> requestPurchase(
            @Valid @RequestBody PurchaseRequestDTO purchaseRequestDTO,
            HttpServletRequest request
    ) {
        return purchaseService.request(purchaseRequestDTO, request); // Panggil method di service untuk request purchase
    }

    // Endpoint untuk mendapatkan detail purchase request berdasarkan no
    @GetMapping("/request/{no}")
//    @PreAuthorize("hasAuthority('PurchaseRequest')")
    public ResponseEntity<Object> getPurchaseRequestByNo(@PathVariable String no, HttpServletRequest request) {
        return purchaseService.findByNo(no, request);
    }

    // Endpoint untuk approval purchase request
    @PutMapping("/request/approve/{no}")
//    @PreAuthorize("hasAuthority('PurchaseRequest')")
    public ResponseEntity<Object> approvePurchaseRequest(
            @PathVariable String no,
            @RequestBody(required = false) PurchaseRequest purchaseRequest,
            HttpServletRequest request
    ) {
        return purchaseService.approve(no, purchaseRequest, request); // Panggil method approve dari service
    }

    // Endpoint untuk reject purchase request
    @PutMapping("/request/reject/{no}")
//    @PreAuthorize("hasAuthority('PurchaseRequest')")
    public ResponseEntity<Object> rejectPurchaseRequest(
            @PathVariable String no,
            @RequestBody(required = false) PurchaseRequest purchaseRequest,
            HttpServletRequest request
    ) {
        return purchaseService.reject(no, purchaseRequest, request); // Panggil method reject dari service
    }
}
