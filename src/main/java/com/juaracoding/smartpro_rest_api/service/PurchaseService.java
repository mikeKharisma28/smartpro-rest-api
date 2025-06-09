package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.core.IApproval;
import com.juaracoding.smartpro_rest_api.core.IRequest;
import com.juaracoding.smartpro_rest_api.dto.validation.PurchaseRequestDTO;
import com.juaracoding.smartpro_rest_api.model.PurchaseRequest;
import com.juaracoding.smartpro_rest_api.repo.PurchaseRequestRepo;
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
public class PurchaseService implements IRequest<PurchaseRequest>, IApproval<PurchaseRequest> {

    private final String pchReqPrefix = "PCH";
    private final Random random = new Random();

    private final PurchaseRequestRepo purchaseRequestRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public PurchaseService(PurchaseRequestRepo purchaseRequestRepo, ModelMapper modelMapper) {
        this.purchaseRequestRepo = purchaseRequestRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Object> request(PurchaseRequest purchaseRequest, HttpServletRequest request) {
        purchaseRequest.setPurchaseRequestNo(generateNo(pchReqPrefix));
        purchaseRequest.setStatus(0); // draft
        purchaseRequest.setCreatedBy(getUserIdFromSecurityContext()); // Ambil user ID dari Security Context
        PurchaseRequest saved = purchaseRequestRepo.save(purchaseRequest);
        return ResponseEntity.ok(saved);
    }

    // overload dengan DTO
    public ResponseEntity<Object> request(PurchaseRequestDTO dto, HttpServletRequest request) {
        PurchaseRequest purchaseRequest = parseToModel(dto);
        return request(purchaseRequest, request);
    }

    @Override
    public ResponseEntity<Object> findByNo(String no, HttpServletRequest request) {
        return purchaseRequestRepo.findById(no)
                .map(purchaseRequest -> ResponseEntity.ok((Object) purchaseRequest)) // Cast ke Object
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        return ResponseEntity.ok(purchaseRequestRepo.findAll(pageable));
    }

    @Override
    public ResponseEntity<Object> approve(String no, PurchaseRequest purchaseRequest, HttpServletRequest request) {
        return (ResponseEntity<Object>) purchaseRequestRepo.findById(no).map(data -> {
            if (data.getStatus() == 1) {
                return ResponseEntity.badRequest().body("Request already approved");
            }
            data.setStatus(1);
            data.setUpdatedBy(getUserIdFromSecurityContext()); // Ambil user ID dari Security Context
            PurchaseRequest updated = purchaseRequestRepo.save(data);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Object> reject(String no, PurchaseRequest purchaseRequest, HttpServletRequest request) {
        return (ResponseEntity<Object>) purchaseRequestRepo.findById(no).map(data -> {
            if (data.getStatus() == 2) {
                return ResponseEntity.badRequest().body("Request already rejected");
            }
            data.setStatus(2);
            data.setUpdatedBy(getUserIdFromSecurityContext()); // Ambil user ID dari Security Context
            PurchaseRequest updated = purchaseRequestRepo.save(data);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
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

    // Ambil user ID dari SecurityContext untuk authentication
    private Long getUserIdFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Asumsi ID disimpan di username atau bisa sesuaikan sesuai implementasi userDetails
            return Long.valueOf(userDetails.getUsername());
        }

        return 0L; // Default untuk user tidak terautentikasi (anonymous)
    }
}
