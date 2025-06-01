package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.core.IApproval;
import com.juaracoding.smartpro_rest_api.core.IRequest;
import com.juaracoding.smartpro_rest_api.dto.validation.PurchaseRequestDTO;
import com.juaracoding.smartpro_rest_api.model.PurchaseRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Random;

@Service
@Transactional
public class PurchaseService implements IRequest<PurchaseRequest>, IApproval<PurchaseRequest> {
    private final String pchReqPrefix = "PCH";
    private final String pchOrdPrefix = "PCO";

    @Autowired
    private ModelMapper modelMapper;

    private Random random = new Random();

    @Override
    public ResponseEntity<Object> request(PurchaseRequest purchaseRequest, HttpServletRequest request) {
        purchaseRequest.setPurchaseRequestNo(generateNo(pchReqPrefix));
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

    // additional functions
    public PurchaseRequest parseToModel(PurchaseRequestDTO purchaseRequestDTO) {
        return modelMapper.map(purchaseRequestDTO, PurchaseRequest.class);
    }

    private String generateNo(String prefix) {
        Long number = random.nextLong(1000000000, 9999999999L);
        String year = String.valueOf(Year.now().getValue()).substring(2);
        return String.format("%s-%s%s", prefix, year, number.toString());
    }
}
