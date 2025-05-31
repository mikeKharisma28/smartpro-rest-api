package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.core.IApproval;
import com.juaracoding.smartpro_rest_api.core.IRequest;
import com.juaracoding.smartpro_rest_api.model.ProcurementRequest;
import com.juaracoding.smartpro_rest_api.model.PurchaseRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PurchaseService implements IRequest<PurchaseRequest>, IApproval<PurchaseRequest> {
    @Override
    public ResponseEntity<Object> request(PurchaseRequest purchaseRequest, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByNo(String no, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> approve(String no, PurchaseRequest purchaseRequest, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> reject(String no, PurchaseRequest purchaseRequest, HttpServletRequest request) {
        return null;
    }
}
