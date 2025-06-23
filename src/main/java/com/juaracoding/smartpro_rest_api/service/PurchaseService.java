package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.core.IApproval;
import com.juaracoding.smartpro_rest_api.core.IRequest;
import com.juaracoding.smartpro_rest_api.dto.report.RepProcurementDTO;
import com.juaracoding.smartpro_rest_api.dto.report.RepPurchaseDTO;
import com.juaracoding.smartpro_rest_api.dto.response.ResProcurementRequestDTO;
import com.juaracoding.smartpro_rest_api.dto.response.ResPurchaseRequestDTO;
import com.juaracoding.smartpro_rest_api.dto.validation.PurchaseRequestDTO;
import com.juaracoding.smartpro_rest_api.model.ProcurementRequest;
import com.juaracoding.smartpro_rest_api.model.PurchaseRequest;
import com.juaracoding.smartpro_rest_api.repo.PurchaseRequestRepo;
import com.juaracoding.smartpro_rest_api.util.GlobalFunction;
import com.juaracoding.smartpro_rest_api.util.GlobalResponse;
import com.juaracoding.smartpro_rest_api.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/***
 * Author: Alfin, 2025-06-08
 * Last updated by: Michael, 2025-06-21
 * Bugs fixed:
 *  - Standardize body responses
 *  - Added purchase approval (approve and reject)
 *  - Fixed truncated purchase number
 *  - Added function for purchase request
 */

@Service
@Transactional
public class PurchaseService implements IRequest<PurchaseRequest>, IApproval<PurchaseRequest> {

    private final String pchReqPrefix = "PCH";
    private final Random random = new Random();

    private final PurchaseRequestRepo purchaseRequestRepo;
    private final ModelMapper modelMapper;
    private final TransformPagination tp;

    @Autowired
    public PurchaseService(PurchaseRequestRepo purchaseRequestRepo, ModelMapper modelMapper, TransformPagination tp) {
        this.purchaseRequestRepo = purchaseRequestRepo;
        this.modelMapper = modelMapper;
        this.tp = tp;
    }

    @Override
    public ResponseEntity<Object> request(PurchaseRequest purchaseRequest, HttpServletRequest request) {
        purchaseRequest.setPurchaseRequestNo(generateNo(pchReqPrefix));
        purchaseRequest.setStatus((short) 0); // draft
        purchaseRequest.setCreatedBy(getUserIdFromSecurityContext()); // Ambil user ID dari Security Context
        PurchaseRequest saved = purchaseRequestRepo.save(purchaseRequest);
        return ResponseEntity.ok(saved);
    }

    // overload dengan DTO
    public ResponseEntity<Object> request(PurchaseRequestDTO dto, HttpServletRequest request) {
        PurchaseRequest purchaseRequest = parseToModel(dto);
        return request(purchaseRequest, request);
    }

    /***
     * Author: Michael, 2025-06-21
     * Desc: Create a purchase request based on approved procurement request
     * @param procurementRequest
     * @param request
     * @return
     */
    public ResponseEntity<Object> request(ProcurementRequest procurementRequest, HttpServletRequest request) {
        Map<String, Object> dataSession = GlobalFunction.extractToken(request);
        try {
            PurchaseRequest purchaseRequest = new PurchaseRequest();

            // get data from procurement request
            purchaseRequest.setUnit(procurementRequest.getUnit());
            purchaseRequest.setProcurementRequest(procurementRequest);

            purchaseRequest.setPurchaseRequestNo(generateNo(pchReqPrefix));
            purchaseRequest.setStatus((short) 0); // draft
            purchaseRequest.setCreatedBy(Long.parseLong(dataSession.get("staffId").toString())); // Ambil user ID dari session

            purchaseRequestRepo.save(purchaseRequest);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE041", request);
        }

        return GlobalResponse.dataCreated(request);
    }

    @Override
    public ResponseEntity<Object> findByNo(String no, HttpServletRequest request) {
        ResPurchaseRequestDTO requestDTO = null;
        try {
            if (no == null) {
                return GlobalResponse.bodyParamRequestNull("Param id is not provided!", "AUT04FV041", request);

            }
            Optional<PurchaseRequest> opData = purchaseRequestRepo.findById(no);
            if (opData.isEmpty()) {
                return GlobalResponse.dataNotFound("AUT04FV042", request);
            }
            PurchaseRequest existingData = opData.get();
            requestDTO = parseToDTO(existingData);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE041", request);
        }

        return GlobalResponse.dataFound(requestDTO, request);

//        return purchaseRequestRepo.findById(no)
//                .map(purchaseRequest -> ResponseEntity.ok((Object) purchaseRequest)) // Cast ke Object
//                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
//        return ResponseEntity.ok(purchaseRequestRepo.findAll(pageable));

        Page<PurchaseRequest> page = null;
        List<PurchaseRequest> list = null;
        List<RepPurchaseDTO> listDTO = null;
        Map<String, Object> data = null;
        try {
            page = purchaseRequestRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("PRC01FV011", request);
            }
            listDTO = parseToListDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "PRC01FE011", request);
        }
        return GlobalResponse.dataFound(data, request);
    }

    @Override
    public ResponseEntity<Object> approve(String no, PurchaseRequest purchaseRequest, HttpServletRequest request) {
        ResponseEntity<Object> response = null;
        Map<String, Object> dataSession = GlobalFunction.extractToken(request);

        try {
            Optional<PurchaseRequest> opData = purchaseRequestRepo.findById(no);

            if (opData.isEmpty()) {
                return GlobalResponse.dataNotFound("PRC02FV012", request);
            }

            PurchaseRequest data = opData.get();
            data.setStatus((short) 1); // approved
            data.setUpdatedBy(Long.parseLong(dataSession.get("staffId").toString()));
            purchaseRequestRepo.save(data);
            return GlobalResponse.dataUpdated(request);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "PRC02FE012", request);
        }
    }

    @Override
    public ResponseEntity<Object> reject(String no, PurchaseRequest purchaseRequest, HttpServletRequest request) {
        ResponseEntity<Object> response = null;
        Map<String, Object> dataSession = GlobalFunction.extractToken(request);

        try {
            Optional<PurchaseRequest> opData = purchaseRequestRepo.findById(no);

            if (opData.isEmpty()) {
                return GlobalResponse.dataNotFound("PRC02FV012", request);
            }

            PurchaseRequest data = opData.get();
            data.setStatus((short) 2); // rejected
            data.setUpdatedBy(Long.parseLong(dataSession.get("staffId").toString()));
            purchaseRequestRepo.save(data);
            return GlobalResponse.dataUpdated(request);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "PRC02FE012", request);
        }
    }

    // additional functions
    public PurchaseRequest parseToModel(PurchaseRequestDTO purchaseRequestDTO) {
        return modelMapper.map(purchaseRequestDTO, PurchaseRequest.class);
    }

    public ResPurchaseRequestDTO parseToDTO(PurchaseRequest purchaseRequest) {
        return modelMapper.map(purchaseRequest, ResPurchaseRequestDTO.class);
    }

    public List<RepPurchaseDTO> parseToListDTO(List<PurchaseRequest> listPurchaseRequest) {
        return modelMapper.map(listPurchaseRequest, new TypeToken<List<RepPurchaseDTO>>() {
        }.getType());
    }

    private String generateNo(String prefix) {
        Long number = random.nextLong(100000000, 999999999L);
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
