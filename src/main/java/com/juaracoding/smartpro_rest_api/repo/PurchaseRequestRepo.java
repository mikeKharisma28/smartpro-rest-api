package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.PurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseRequestRepo extends JpaRepository<PurchaseRequest, String> {
    Optional<PurchaseRequest> findById(String id);
    Optional<PurchaseRequest> findByStatus(Integer status);
}
