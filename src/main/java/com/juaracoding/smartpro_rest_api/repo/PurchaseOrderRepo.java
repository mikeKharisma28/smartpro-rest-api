package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, String> {
    // Menggunakan findById untuk mencari PurchaseOrder berdasarkan nomor
    Optional<PurchaseOrder> findById(String id);
}
