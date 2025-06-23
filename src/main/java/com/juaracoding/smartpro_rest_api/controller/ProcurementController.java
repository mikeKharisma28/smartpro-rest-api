package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.config.OtherConfig;
import com.juaracoding.smartpro_rest_api.dto.validation.ValidProcurementRequestDTO;
import com.juaracoding.smartpro_rest_api.service.ProcurementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/***
 * Author: Michael, 2025-05-31
 * Functions added by: Riyan, 2025-06-16
 */

@RestController
@RequestMapping("procurement")
public class ProcurementController {

    @Autowired
    private ProcurementService procurementService;

    @GetMapping("/request")
//    @PreAuthorize("hasAuthority('ProcurementRequest')")
    public ResponseEntity<Object> requestList(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("procurementNo"));
        return procurementService.findAll(pageable, request);
    }


    @PostMapping("/request")
//    @PreAuthorize("hasAuthority('ProcurementRequest')")
    public ResponseEntity<Object> requestProcurement(
            @Valid @RequestBody ValidProcurementRequestDTO validProcurementRequestDTO,
            HttpServletRequest request
    ) {
        return procurementService.request(validProcurementRequestDTO, request);
    }

    @PutMapping("/{id}/approve")
//    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> approve(@Valid @RequestBody ValidProcurementRequestDTO validProcurementRequestDTO,
                                          @PathVariable String id,
                                          HttpServletRequest request) {
        return procurementService.approve(id, procurementService.parseToModel(validProcurementRequestDTO), request);
    }

    @PutMapping("/{id}/reject")
//    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> reject(@Valid @RequestBody ValidProcurementRequestDTO validProcurementRequestDTO,
                                         @PathVariable String id,
                                         HttpServletRequest request) {
        return procurementService.reject(id, procurementService.parseToModel(validProcurementRequestDTO), request);
    }

}
