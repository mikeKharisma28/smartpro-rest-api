package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.service.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("procurement")
public class ProcurementController {

    @Autowired
    private ProcurementService procurementService;

    @GetMapping("/request")
    @PreAuthorize("hasAuthority('ProcurementRequest')")
    public RequestEntity<Object> requestList() {
        return null;
    }
}
