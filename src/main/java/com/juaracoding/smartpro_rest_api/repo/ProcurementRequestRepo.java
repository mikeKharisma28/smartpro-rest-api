package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.ProcurementRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcurementRequestRepo extends JpaRepository<ProcurementRequest, Long> {
    Optional<ProcurementRequest> findById(String id);
    Optional<ProcurementRequest> findByStatus(Integer status);
}