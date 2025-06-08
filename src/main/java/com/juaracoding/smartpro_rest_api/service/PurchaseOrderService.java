package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.dto.validation.PurchaseOrderDTO;
import com.juaracoding.smartpro_rest_api.model.PurchaseOrder;
import com.juaracoding.smartpro_rest_api.repo.PurchaseOrderRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    // Method untuk membuat PurchaseOrder
    public ResponseEntity<Object> createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO, HttpServletRequest request) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setQuantityOrdered(purchaseOrderDTO.getQuantityOrdered());
        purchaseOrder.setUnitPrice(purchaseOrderDTO.getUnitPrice());
        purchaseOrder.setUnit(purchaseOrderDTO.getUnit());
        purchaseOrder.setTotalAmount(purchaseOrderDTO.getTotalAmount());
        purchaseOrder.setStatus(purchaseOrderDTO.getStatus());

        // Simpan PurchaseOrder ke database
        PurchaseOrder savedOrder = purchaseOrderRepo.save(purchaseOrder);

        return ResponseEntity.ok(savedOrder); // Kembalikan PurchaseOrder yang sudah disimpan
    }

    // Method untuk mengambil daftar PurchaseOrder
    public ResponseEntity<Object> findAll(PageRequest pageRequest, HttpServletRequest request) {
        return ResponseEntity.ok(purchaseOrderRepo.findAll(pageRequest));
    }
    public ResponseEntity<PurchaseOrder> findByPurchaseOrderNo(String purchaseOrderNo) {
        return purchaseOrderRepo.findById(purchaseOrderNo)
                .map(ResponseEntity::ok) // Jika ditemukan, kembalikan status OK
                .orElse(ResponseEntity.notFound().build()); // Jika tidak ditemukan, kembalikan status 404
    }

}
