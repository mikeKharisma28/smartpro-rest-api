package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.config.OtherConfig;
import com.juaracoding.smartpro_rest_api.dto.validation.RoleDTO;
import com.juaracoding.smartpro_rest_api.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("role")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAuthority('Role')")
    public ResponseEntity<Object> save(@Valid @RequestBody RoleDTO roleDTO,
                                       HttpServletRequest request){
        return roleService.save(roleService.mapToRole(roleDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Role')")
    public ResponseEntity<Object> update(@Valid @RequestBody RoleDTO roleDTO,
                                         @PathVariable Long id,
                                         HttpServletRequest request){
        return roleService.update(id, roleService.mapToRole(roleDTO),request);
    }

    /** defaultSearch
     * Ketika role dibuka pertama kali, api yang di hit adalah api ini ....
     */
    @GetMapping
    @PreAuthorize("hasAuthority('Role')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return roleService.findAll(pageable,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Role')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return roleService.findById(id,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Role')")
    public ResponseEntity<Object> findByParam(
            @PathVariable String sort,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable Integer page,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request){
        Pageable pageable = null;
        sortBy = sortColumn(sortBy);
        switch (sort) {
            case "desc":pageable = PageRequest.of(page,size, Sort.by("id").descending());break;
            default:pageable = PageRequest.of(page,size, Sort.by("id"));break;
        }
        return roleService.findByParam(pageable,column,value,request);
    }

    @GetMapping("/download-excel")
    @PreAuthorize("hasAuthority('Role')")
    public void downloadExcel(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        roleService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/download-pdf")
    @PreAuthorize("hasAuthority('Role')")
    public void downloadPdf(@RequestParam String column,
                            @RequestParam String value,
                            HttpServletRequest request,
                            HttpServletResponse response){
        roleService.generateToPDF(column,value,request,response);
    }

    private String sortColumn(String column){
        switch (column){
            case "name":column="name";break;
            default:column="id";break;
        }
        return column;
    }
}