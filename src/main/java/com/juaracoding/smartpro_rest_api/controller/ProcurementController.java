package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.config.OtherConfig;
import com.juaracoding.smartpro_rest_api.dto.validation.MenuDTO;
import com.juaracoding.smartpro_rest_api.dto.validation.ProcurementRequestDTO;
import com.juaracoding.smartpro_rest_api.service.ProcurementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("procurement")
public class ProcurementController {

    @Autowired
    private ProcurementService procurementService;

    @GetMapping("/request")
    @PreAuthorize("hasAuthority('ProcurementRequest')")
    public ResponseEntity<Object> requestList(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return procurementService.findAll(pageable, request);
    }


    @PostMapping("/request")
    @PreAuthorize("hasAuthority('ProcurementRequest')")
    public ResponseEntity<Object> requestProcurement(
            @Valid @RequestBody ProcurementRequestDTO procurementRequestDTO,
            HttpServletRequest request
    ) {
        return procurementService.request(procurementService.parseToModel(procurementRequestDTO), request);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> approve(@Valid @RequestBody ProcurementRequestDTO procurementRequestDTO,
                                         @PathVariable String id,
                                         HttpServletRequest request){
        return procurementService.approve(id,procurementService.parseToModel(procurementRequestDTO),request);
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> reject(@Valid @RequestBody ProcurementRequestDTO procurementRequestDTO,
                                         @PathVariable String id,
                                         HttpServletRequest request){
        return procurementService.reject(id,procurementService.parseToModel(procurementRequestDTO),request);
    }

}
