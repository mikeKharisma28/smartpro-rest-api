package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/***
 * Author: Alfin, 2025-06-09
 * Edited by: Michael, 2025-06-20
 */

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, String> {
    // Menggunakan findProfileById untuk mencari PurchaseOrder berdasarkan nomor
    Optional<PurchaseOrder> findById(String id);

    List<PurchaseOrder> findByPurchaseOrderNoContainsIgnoreCase(String value);
//
//    List<PurchaseOrder> findByStatusContainsIgnoreCase(Short status);
//
//    List<PurchaseOrder> findByUnitContainsIgnoreCase(String value);
}
