package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.config.OtherConfig;
import com.juaracoding.smartpro_rest_api.dto.validation.DivisionDTO;
import com.juaracoding.smartpro_rest_api.service.DivisionService;
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

@RestController
@RequestMapping("division")
public class DivisionController {

    @Autowired
    private DivisionService divisionService;

    @PostMapping
    @PreAuthorize("hasAuthority('Division')")
    public ResponseEntity<Object> save(@Valid @RequestBody DivisionDTO divisionDTO,
                                       HttpServletRequest request){
        return divisionService.save(divisionService.mapToDivision(divisionDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Division')")
    public ResponseEntity<Object> update(@Valid @RequestBody DivisionDTO divisionDTO,
                                         @PathVariable Long id,
                                         HttpServletRequest request){
        return divisionService.update(id,divisionService.mapToDivision(divisionDTO),request);
    }


    /** defaultSearch
     * Ketika menu dibuka pertama kali, api yang di hit adalah api ini ....
     */

    @GetMapping
    @PreAuthorize("hasAuthority('Division')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return divisionService.findAll(pageable, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Division')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return divisionService.findById(id, request);
    }

    /** api ketika user sudah melakukan interaksi di menu ini
     * searching, paging, sorting
     * localhost:8080/group-menu/kodok/cumi/0?size=10&column=nama&value=user
     */
    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Division')")
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
        return divisionService.findByParam(pageable,column,value,request);
    }

    @GetMapping("/download-excel")
    @PreAuthorize("hasAuthority('Division')")
    public void downloadExcel(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        divisionService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/download-pdf")
    @PreAuthorize("hasAuthority('Division')")
    public void downloadPdf(@RequestParam String column,
                            @RequestParam String value,
                            HttpServletRequest request,
                            HttpServletResponse response){
        divisionService.generateToPDF(column,value,request,response);
    }

    private String sortColumn(String column){
        switch (column){
            case "name":column="name";break;
            default:column="id";break;
        }
        return column;
    }
}
