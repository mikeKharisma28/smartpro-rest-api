package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.core.IApproval;
import com.juaracoding.smartpro_rest_api.core.IRequest;
import com.juaracoding.smartpro_rest_api.dto.validation.ProcurementRequestDTO;
import com.juaracoding.smartpro_rest_api.model.ProcurementRequest;
import com.juaracoding.smartpro_rest_api.repo.ProcurementRequestRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Random;

@Service
@Transactional
public class ProcurementService implements IRequest<ProcurementRequest>, IApproval<ProcurementRequest> {
    private final String procPrefix = "PRC";

    @Autowired
    private ProcurementRequestRepo procurementRequestRepo;

    @Autowired
    private ModelMapper modelMapper;

    private Random random = new Random();

    @Autowired
    public ProcurementService(ProcurementRequestRepo procurementRequestRepo, ModelMapper modelMapper) {
        this.procurementRequestRepo = procurementRequestRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Object> request(ProcurementRequest procurementRequest, HttpServletRequest request) {
        procurementRequest.setProcurementNo(generateNo(procPrefix));
        procurementRequest.setRequestedQuantity(procurementRequest.getRequestedQuantity());
        procurementRequest.setItemDescription(procurementRequest.getItemDescription());
        procurementRequest.setUnit(procurementRequest.getUnit());
        procurementRequest.setRequestedQuantity(procurementRequest.getRequestedQuantity());
        procurementRequest.setItemName(procurementRequest.getItemName());
        procurementRequest.setExpectedDate(procurementRequest.getExpectedDate());
        procurementRequest.setItemDescription(procurementRequest.getItemDescription());
        procurementRequest.setStatus(0); // draft
        procurementRequest.setCreatedBy(getUserIdFromSecurityContext());
        ProcurementRequest saved = procurementRequestRepo.save(procurementRequest);
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<Object> request(ProcurementRequestDTO procurementRequestDTO, HttpServletRequest request) {
        ProcurementRequest procurementRequest = parseToModel(procurementRequestDTO);
        return request(procurementRequest, request);
    }


    @Override
    public ResponseEntity<Object> findByNo(String no, HttpServletRequest request) {
        return procurementRequestRepo.findByProcurementNo(no)
                .map(procurementRequest -> ResponseEntity.ok((Object) procurementRequest)) // Cast ke Object
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        return ResponseEntity.ok(procurementRequestRepo.findAll(pageable));
    }

    @Override
    public ResponseEntity<Object> approve(String no, ProcurementRequest procurementRequest, HttpServletRequest request) {
        return (ResponseEntity<Object>) procurementRequestRepo.findByProcurementNo(no).map(data -> {
            if (data.getStatus() == 1) {
                return ResponseEntity.badRequest().body("Request already approved");
            }
            data.setStatus(1);
            data.setUpdatedBy(getUserIdFromSecurityContext()); // Ambil user ID dari Security Context
            ProcurementRequest updated = procurementRequestRepo.save(data);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Object> reject(String no, ProcurementRequest procurementRequest, HttpServletRequest request) {
        return (ResponseEntity<Object>) procurementRequestRepo.findByProcurementNo(no).map(data -> {
            if (data.getStatus() == 2) {
                return ResponseEntity.badRequest().body("Request already approved");
            }
            data.setStatus(1);
            data.setUpdatedBy(getUserIdFromSecurityContext()); // Ambil user ID dari Security Context
            ProcurementRequest updated = procurementRequestRepo.save(data);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    // additional functions
    public ProcurementRequest parseToModel(ProcurementRequestDTO procurementRequestDTO) {
        return modelMapper.map(procurementRequestDTO, ProcurementRequest.class);
    }

    private Long getUserIdFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Asumsi ID disimpan di username atau bisa sesuaikan sesuai implementasi userDetails
            return Long.valueOf(userDetails.getUsername());
        }

        return 0L; // Default untuk user tidak terautentikasi (anonymous)
    }

    private String generateNo(String prefix) {
        Long number = random.nextLong(1000000000, 9999999999L);
        String year = String.valueOf(Year.now().getValue()).substring(2);
        return String.format("%s-%s%s", prefix, year, number.toString());
    }

}