package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.config.OtherConfig;
import com.juaracoding.smartpro_rest_api.dto.validation.MenuDTO;
import com.juaracoding.smartpro_rest_api.service.MenuService;
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
import org.springframework.web.servlet.HandlerMapping;

@RestController
@RequestMapping("menu")
public class MenuController {


    @Autowired
    private MenuService menuService;

    @Qualifier("resourceHandlerMapping")
    @Autowired
    private HandlerMapping resourceHandlerMapping;


    @PostMapping
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> save(@Valid @RequestBody MenuDTO menuDTO,
                                       HttpServletRequest request){
        return menuService.save(menuService.mapToMenu(menuDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> update(@Valid @RequestBody MenuDTO menuDTO,
                                         @PathVariable Long id,
                                         HttpServletRequest request){
        return menuService.update(id, menuService.mapToMenu(menuDTO),request);
    }

    /** defaultSearch
     * Ketika menu dibuka pertama kali, api yang di hit adalah api ini ....
     */
    @GetMapping
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0, OtherConfig.getDefaultPaginationSize(), Sort.by("id"));
        return menuService.findAll(pageable,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return menuService.findById(id,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Menu')")
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
        return menuService.findByParam(pageable,column,value,request);
    }

    @GetMapping("/download-excel")
    @PreAuthorize("hasAuthority('Menu')")
    public void downloadExcel(@RequestParam String column,
                              @RequestParam String value,
                              HttpServletRequest request,
                              HttpServletResponse response){
        menuService.downloadReportExcel(column,value,request,response);
    }

    @GetMapping("/download-pdf")
    @PreAuthorize("hasAuthority('Menu')")
    public void downloadPdf(@RequestParam String column,
                            @RequestParam String value,
                            HttpServletRequest request,
                            HttpServletResponse response){
        menuService.generateToPDF(column,value,request,response);
    }

    private String sortColumn(String column){
        switch (column){
            case "name":column="name";break;
            case "CreatedBy":column="CreatedBy";break;
            case "CreatedDate":column="CreatedDate";break;
            case "UpdatedBy":column="UpdatedBy";break;
            case "UpdatedDate":column="UpdatedDate";break;
            default:column="id";break;
        }
        return column;
    }
}
