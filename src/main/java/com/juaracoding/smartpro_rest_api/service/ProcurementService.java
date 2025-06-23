package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.core.IApproval;
import com.juaracoding.smartpro_rest_api.core.IRequest;
import com.juaracoding.smartpro_rest_api.dto.report.RepProcurementDTO;
import com.juaracoding.smartpro_rest_api.dto.response.ResProcurementRequestDTO;
import com.juaracoding.smartpro_rest_api.dto.validation.ValidProcurementRequestDTO;
import com.juaracoding.smartpro_rest_api.model.ProcurementRequest;
import com.juaracoding.smartpro_rest_api.repo.ProcurementRequestRepo;
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
 * Author: Michael, 2025-05-31
 * Added & Edited by: Riyan, 2025-06-16
 * Last updated by: Michael, 2025-06-20
 * Bugs fixed:
 * - Standardize body responses
 * - Fixed procurement approval (approve and reject)
 * - Fixed truncated procurement number
 */

@Service
@Transactional
public class ProcurementService implements IRequest<ProcurementRequest>, IApproval<ProcurementRequest> {
    private final String procPrefix = "PRC";
    private Random random = new Random();

    private final ProcurementRequestRepo procurementRequestRepo;
    private final ModelMapper modelMapper;
    private final TransformPagination tp;

    @Autowired
    public ProcurementService(ProcurementRequestRepo procurementRequestRepo, ModelMapper modelMapper, TransformPagination tp) {
        this.procurementRequestRepo = procurementRequestRepo;
        this.modelMapper = modelMapper;
        this.tp = tp;
    }

    @Override
    public ResponseEntity<Object> request(ProcurementRequest procurementRequest, HttpServletRequest request) {
        Map<String, Object> dataSession = GlobalFunction.extractToken(request);

        try {
            procurementRequest.setProcurementNo(generateNo(procPrefix));
            procurementRequest.setStatus((short) 0); // submitted
            procurementRequest.setCreatedBy(Long.parseLong(dataSession.get("staffId").toString()));
            procurementRequestRepo.save(procurementRequest);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE041", request);
        }

        return GlobalResponse.dataCreated(request);
    }

    public ResponseEntity<Object> request(ValidProcurementRequestDTO validProcurementRequestDTO, HttpServletRequest request) {
        ProcurementRequest procurementRequest = parseToModel(validProcurementRequestDTO);
        return request(procurementRequest, request);
    }

    @Override
    public ResponseEntity<Object> findByNo(String no, HttpServletRequest request) {
        ResProcurementRequestDTO requestDTO = null;
        try {
            if (no == null) {
                return GlobalResponse.bodyParamRequestNull("Param id is not provided!", "AUT04FV041", request);

            }
            Optional<ProcurementRequest> opData = procurementRequestRepo.findById(no);
            if (opData.isEmpty()) {
                return GlobalResponse.dataNotFound("AUT04FV042", request);
            }
            ProcurementRequest existingData = opData.get();
            requestDTO = parseToDTO(existingData);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE041", request);
        }

        return GlobalResponse.dataFound(requestDTO, request);

//        return procurementRequestRepo.findById(no)
//                .map(procurementRequest -> ResponseEntity.ok((Object) procurementRequest)) // Cast ke Object
//                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
//        return ResponseEntity.ok(procurementRequestRepo.findAll(pageable));
        Page<ProcurementRequest> page = null;
        List<ProcurementRequest> list = null;
        List<RepProcurementDTO> listDTO = null;
        Map<String, Object> data = null;
        try {
            page = procurementRequestRepo.findAll(pageable);
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
    public ResponseEntity<Object> approve(String no, ProcurementRequest procurementRequest, HttpServletRequest request) {
        ResponseEntity<Object> response = null;
        Map<String, Object> dataSession = GlobalFunction.extractToken(request);

        try {
            Optional<ProcurementRequest> opData = procurementRequestRepo.findById(no);

            if (opData.isEmpty()) {
                return GlobalResponse.dataNotFound("PRC02FV012", request);
            }

            ProcurementRequest data = opData.get();
            data.setStatus((short) 1); // approved
            data.setUpdatedBy(Long.parseLong(dataSession.get("staffId").toString()));
            procurementRequestRepo.save(data);
            return GlobalResponse.dataUpdated(request);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "PRC02FE012", request);
        }
    }

    @Override
    public ResponseEntity<Object> reject(String no, ProcurementRequest procurementRequest, HttpServletRequest request) {
        ResponseEntity<Object> response = null;
        Map<String, Object> dataSession = GlobalFunction.extractToken(request);

        try {
            Optional<ProcurementRequest> opData = procurementRequestRepo.findById(no);

            if (opData.isEmpty()) {
                return GlobalResponse.dataNotFound("PRC03FV012", request);
            }

            ProcurementRequest data = opData.get();
            data.setStatus((short) 2); // rejected
            data.setUpdatedBy(Long.parseLong(dataSession.get("staffId").toString()));
            procurementRequestRepo.save(data);
            return GlobalResponse.dataUpdated(request);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "PRC03FE012", request);
        }
    }

    // additional functions
    public ProcurementRequest parseToModel(ValidProcurementRequestDTO validProcurementRequestDTO) {
        return modelMapper.map(validProcurementRequestDTO, ProcurementRequest.class);
    }

    public ResProcurementRequestDTO parseToDTO(ProcurementRequest procurementRequest) {
        return modelMapper.map(procurementRequest, ResProcurementRequestDTO.class);
    }

    public List<RepProcurementDTO> parseToListDTO(List<ProcurementRequest> listProcurementRequest) {
        return modelMapper.map(listProcurementRequest, new TypeToken<List<RepProcurementDTO>>() {
        }.getType());
    }


    // private functions
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
        Long number = random.nextLong(100000000, 999999999L);
        String year = String.valueOf(Year.now().getValue()).substring(2);
        return String.format("%s-%s%s", prefix, year, number.toString());
    }

}