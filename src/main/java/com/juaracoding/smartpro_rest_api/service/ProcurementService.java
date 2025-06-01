package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.core.IApproval;
import com.juaracoding.smartpro_rest_api.core.IRequest;
import com.juaracoding.smartpro_rest_api.dto.validation.ProcurementRequestDTO;
import com.juaracoding.smartpro_rest_api.model.ProcurementRequest;
import com.juaracoding.smartpro_rest_api.util.GlobalResponse;
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
public class ProcurementService implements IRequest<ProcurementRequest>, IApproval<ProcurementRequest> {
    private final String procPrefix = "PRC";

    @Autowired
    private ModelMapper modelMapper;

    private Random random = new Random();

    @Override
    public ResponseEntity<Object> request(ProcurementRequest procurementRequest, HttpServletRequest request) {
        procurementRequest.setProcurementNo(generateNo());
        return GlobalResponse.dataFound(procurementRequest, request);
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
    public ResponseEntity<Object> approve(String no, ProcurementRequest procurementRequest, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> reject(String no, ProcurementRequest procurementRequest, HttpServletRequest request) {
        return null;
    }

    // additional functions
    public ProcurementRequest parseToModel(ProcurementRequestDTO procurementRequestDTO) {
        return modelMapper.map(procurementRequestDTO, ProcurementRequest.class);
    }

    private String generateNo() {
        Long number = random.nextLong(1000000000, 9999999999L);
        String year = String.valueOf(Year.now().getValue()).substring(2);
        return String.format("%s-%s%s", procPrefix, year, number.toString());
    }
}
