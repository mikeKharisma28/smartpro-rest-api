package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.ProcurementRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcurementRequestRepo extends JpaRepository<ProcurementRequest, String> {
    Optional<ProcurementRequest> findById(String procurementNo);
    Optional<ProcurementRequest> findByStatus(Integer status);
}