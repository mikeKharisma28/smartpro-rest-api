package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.dto.validation.PurchaseOrderDTO;
import com.juaracoding.smartpro_rest_api.service.PurchaseOrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("purchase-order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    // Endpoint untuk mendapatkan daftar purchase orders dengan pagination
    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('purchaseOrderList')")
    public ResponseEntity<Object> purchaseOrderList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        return ResponseEntity.ok(purchaseOrderService.findAll(PageRequest.of(page, size), request));
    }

    // Endpoint untuk membuat Purchase Order baru
    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('purchaseOrderCreate')")
    public ResponseEntity<ResponseEntity<Object>> createPurchaseOrder(
            @Valid @RequestBody PurchaseOrderDTO purchaseOrderDTO,
            HttpServletRequest request) {
        // Memanggil service untuk membuat Purchase Order dan mendapatkan ResPurchaseRequestDTO
        ResponseEntity<Object> responseDTO = purchaseOrderService.createPurchaseOrder(purchaseOrderDTO, request);

        // Mengembalikan response dengan status CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Endpoint untuk mengunduh laporan PDF berdasarkan pencarian
    @GetMapping("/report/pdf")
//    @PreAuthorize("hasAuthority('purchaseOrderReportPdf')")
    public void downloadPurchaseOrderReportPDF(
            @RequestParam("column") String column,
            @RequestParam("value") String value,
            HttpServletRequest request,
            HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=purchase_order_report.pdf");
        purchaseOrderService.generateToPDF(column, value, request, response);
    }

    // Endpoint untuk mengunduh laporan Excel berdasarkan pencarian
    @GetMapping("/report/excel")
//    @PreAuthorize("hasAuthority('purchaseOrderReportExcel')")
    public void downloadPurchaseOrderReportExcel(
            @RequestParam("column") String column,
            @RequestParam("value") String value,
            HttpServletRequest request,
            HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=purchase_order_report.xlsx");
        purchaseOrderService.downloadReportExcel(column, value, request, response);
    }

    // Penanganan Error: Menangani kesalahan validasi
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        // Mengembalikan pesan error validasi
        return new ResponseEntity<>(ex.getBindingResult().getAllErrors(), HttpStatus.BAD_REQUEST);
    }
}
