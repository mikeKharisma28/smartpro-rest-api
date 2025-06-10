package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.config.OtherConfig;
import com.juaracoding.smartpro_rest_api.dto.validation.EditStaffDTO;
import com.juaracoding.smartpro_rest_api.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Qualifier("resourceHandlerMapping")
    @PostMapping
    @PreAuthorize("hasAuthority('Staff')")
    public ResponseEntity<Object> save(@Valid @RequestBody EditStaffDTO editStaffDTO,
                                       HttpServletRequest request){
        return staffService.save(staffService.mapToUser(editStaffDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Staff')")
    public ResponseEntity<Object> update(@Valid @RequestBody EditStaffDTO editStaffDTO,
                                         @PathVariable Long id,
                                         HttpServletRequest request){
        return staffService.update(id, staffService.mapToUser(editStaffDTO),request);
    }

    /** defaultSearch
     * Ketika menu dibuka pertama kali, api yang di hit adalah api ini ....
     */
    @GetMapping
    @PreAuthorize("hasAuthority('Staff')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return staffService.findAll(pageable,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Staff')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return staffService.findById(id,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Staff')")
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
        return staffService.findByParam(pageable,column,value,request);
    }

    @GetMapping("/download-excel")
    @PreAuthorize("hasAuthority('Staff')")
    public void downloadExcel(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        staffService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/download-pdf")
    @PreAuthorize("hasAuthority('Staff')")
    public void downloadPdf(@RequestParam String column,
                            @RequestParam String value,
                            HttpServletRequest request,
                            HttpServletResponse response){
        staffService.generateToPDF(column,value,request,response);
    }

    private String sortColumn(String column){
        switch (column){
            case "full-name":column="full-name";break;
            case "phone-number":column="phone-number";break;
            case "username":column="username";break;
            default:column="id";break;
        }
        return column;
    }
}

