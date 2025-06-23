package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.ProcurementRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/***
 * Author : Riyan, 2025-06-16
 * Edited by : Michael, 2025-06-16
 */

public interface ProcurementRequestRepo extends JpaRepository<ProcurementRequest, String> {
    Optional<ProcurementRequest> findById(String procurementNo);
    Optional<ProcurementRequest> findByStatus(Short status);
}