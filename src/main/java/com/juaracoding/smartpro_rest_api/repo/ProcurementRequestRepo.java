package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.ProcurementRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/***
 * Author : Riyan
 * Created date : 2025-06-16
 * Edited by : Michael
 * Edited date : 2025-06-16
 */

public interface ProcurementRequestRepo extends JpaRepository<ProcurementRequest, Long> {
    Optional<ProcurementRequest> findByProcurementNo(String procurementNo);
    Optional<ProcurementRequest> findByStatus(Integer status);
}