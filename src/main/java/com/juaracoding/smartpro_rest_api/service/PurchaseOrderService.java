package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.core.IReport;
import com.juaracoding.smartpro_rest_api.dto.report.RepPurchaseOrderDTO;
import com.juaracoding.smartpro_rest_api.dto.validation.PurchaseOrderDTO;
import com.juaracoding.smartpro_rest_api.model.PurchaseOrder;
import com.juaracoding.smartpro_rest_api.repo.PurchaseOrderRepo;
import com.juaracoding.smartpro_rest_api.util.GlobalFunction;
import com.juaracoding.smartpro_rest_api.util.GlobalResponse;
import com.juaracoding.smartpro_rest_api.util.PdfGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService implements IReport<PurchaseOrder> {

    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;
    @Autowired
    private PdfGenerator pdfGenerator;
    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    // Method untuk membuat PurchaseOrder
    public ResponseEntity<Object> createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO, HttpServletRequest request) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setQuantityOrdered(purchaseOrderDTO.getQuantityOrdered());
        purchaseOrder.setUnitPrice(purchaseOrderDTO.getUnitPrice());
        purchaseOrder.setUnit(purchaseOrderDTO.getUnit());
        purchaseOrder.setTotalAmount(purchaseOrderDTO.getTotalAmount());
        purchaseOrder.setStatus(purchaseOrderDTO.getStatus());

        // Simpan PurchaseOrder ke database
        PurchaseOrder savedOrder = purchaseOrderRepo.save(purchaseOrder);

        return ResponseEntity.ok(savedOrder); // Kembalikan PurchaseOrder yang sudah disimpan
    }

    // Method untuk mengambil daftar PurchaseOrder
    public ResponseEntity<Object> findAll(PageRequest pageRequest, HttpServletRequest request) {
        return ResponseEntity.ok(purchaseOrderRepo.findAll(pageRequest));
    }

    public ResponseEntity<PurchaseOrder> findByPurchaseOrderNo(String purchaseOrderNo) {
        return purchaseOrderRepo.findById(purchaseOrderNo)
                .map(ResponseEntity::ok) // Jika ditemukan, kembalikan status OK
                .orElse(ResponseEntity.notFound().build()); // Jika tidak ditemukan, kembalikan status 404
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<PurchaseOrder> listPurchaseOrder = null;
        try {
            // Mengambil data PurchaseOrder berdasarkan kolom dan nilai pencarian
            switch (column) {
                case "purchaseOrderNo":
                    listPurchaseOrder = purchaseOrderRepo.findByPurchaseOrderNoContainsIgnoreCase(value);
                    break;
                case "status":
                    listPurchaseOrder = purchaseOrderRepo.findByStatusContainsIgnoreCase(value);
                    break;
                case "unit":
                    listPurchaseOrder = purchaseOrderRepo.findByUnitContainsIgnoreCase(value);
                    break;
                default:
                    listPurchaseOrder = purchaseOrderRepo.findAll();
                    break;
            }

            // Cek apakah data ditemukan
            if (listPurchaseOrder.isEmpty()) {
                GlobalResponse.manualResponse(response, GlobalResponse.dataNotFound("Data not found!", request));
                return;
            }

            // Membuat Workbook Excel
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Purchase Orders");

            // Menambahkan Header
            XSSFRow headerRow = sheet.createRow(0);
            String[] columns = {"Purchase Order No", "Unit", "Unit Price", "Quantity Ordered", "Total Amount", "Status"};

            for (int i = 0; i < columns.length; i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Mengisi data PurchaseOrder ke dalam Excel
            int rowNum = 1;
            for (PurchaseOrder order : listPurchaseOrder) {
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(order.getPurchaseOrderNo());
                row.createCell(1).setCellValue(order.getUnit());
                row.createCell(2).setCellValue(String.valueOf(order.getUnitPrice()));
                row.createCell(3).setCellValue(order.getQuantityOrdered());
                row.createCell(4).setCellValue(String.valueOf(order.getTotalAmount()));
                row.createCell(5).setCellValue(order.getStatus());
            }

            // Mengatur ukuran kolom agar lebih rapi
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Menyiapkan Response untuk mendownload file Excel
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"purchase_orders_report.xlsx\"");

            // Menulis Workbook ke Output Stream (response)
            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (Exception e) {
            // Tangani error dan kirim respons kesalahan
            GlobalResponse.manualResponse(response, GlobalResponse.terjadiKesalahan("Error while generating Excel", request));
        }
    }


    @Override
    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<PurchaseOrder> listPurchaseOrder = null;
        try {
            // Mengambil data PurchaseOrder berdasarkan kolom dan nilai pencarian
            switch (column) {
                case "purchaseOrderNo":
                    listPurchaseOrder = purchaseOrderRepo.findByPurchaseOrderNoContainsIgnoreCase(value);
                    break;
                case "status":
                    listPurchaseOrder = purchaseOrderRepo.findByStatusContainsIgnoreCase(value);
                    break;
                case "unit":
                    listPurchaseOrder = purchaseOrderRepo.findByUnitContainsIgnoreCase(value);
                    break;
                default:
                    listPurchaseOrder = purchaseOrderRepo.findAll();
                    break;
            }

            // Cek apakah data ditemukan
            if (listPurchaseOrder.isEmpty()) {
                GlobalResponse.manualResponse(response, GlobalResponse.dataNotFound("Data not found!", request));
                return;
            }

            // Mapping data PurchaseOrder ke DTO
            List<RepPurchaseOrderDTO> listDTO = mapToDTO(listPurchaseOrder);
            int intRepPurchaseOrderDTOSize = listDTO.size();

            // Siapkan data untuk template
            Map<String, Object> mapResponse = new HashMap<>();
            List<String> listTemp = new ArrayList<>();
            List<String> listHelper = new ArrayList<>();

            // Mapping kolom-kolom
            // Asumsi listPurchaseOrder adalah list yang berisi objek PurchaseOrder
            for (PurchaseOrder order : listPurchaseOrder) {
                // Mengonversi setiap order ke DTO (RepPurchaseOrderDTO) atau data lain yang dibutuhkan
                Map<String, Object> mapColumnName = GlobalFunction.convertClassToMap(new RepPurchaseOrderDTO(
                        order.getPurchaseOrderNo(),
                        order.getUnit(),
                        order.getUnitPrice(),
                        order.getQuantityOrdered(),
                        order.getTotalAmount(),
                        order.getStatus()
                ));

                // Menambahkan kolom ke listTemp dan listHelper
                for (Map.Entry<String, Object> m : mapColumnName.entrySet()) {
                    listTemp.add(GlobalFunction.camelToStandard(m.getKey()));  // Mengonversi camelCase ke format standar
                    listHelper.add(m.getKey());  // Menambahkan nama kolom
                }
            }


            // Mapping isi data
            List<Map<String, Object>> listMap = new ArrayList<>();
            for (int i = 0; i < intRepPurchaseOrderDTOSize; i++) {
                Map<String, Object> mapTemp = GlobalFunction.convertClassToMap(listDTO.get(i));
                listMap.add(mapTemp);
            }

            // Mengisi parameter template untuk laporan
            mapResponse.put("title", "REPORT PURCHASE ORDER DATA");
            mapResponse.put("listKolom", listTemp);
            mapResponse.put("listHelper", listHelper);
            mapResponse.put("listContent", listMap);
            mapResponse.put("totalData", intRepPurchaseOrderDTOSize);
            mapResponse.put("username", "Paul");

            // Membuat HTML dari template
            Context context = new Context();
            context.setVariables(mapResponse);
            String strHtml = springTemplateEngine.process("global-report", context);

            // Konversi HTML ke PDF menggunakan pdfGenerator
            pdfGenerator.htmlToPdf(strHtml, "purchaseorder", response);

        } catch (Exception e) {
            // Tangani error dan kirim respons kesalahan
            GlobalResponse.manualResponse(response, GlobalResponse.terjadiKesalahan("Error", request));
        }
    }

    private List<RepPurchaseOrderDTO> mapToDTO(List<PurchaseOrder> listPurchaseOrder) {
        List<RepPurchaseOrderDTO> dtoList = new ArrayList<>();

        if (listPurchaseOrder != null && !listPurchaseOrder.isEmpty()) {
            dtoList = listPurchaseOrder.stream()
                    .map(orders -> new RepPurchaseOrderDTO(
                            orders.getPurchaseOrderNo(),
                            orders.getUnit(),
                            orders.getUnitPrice(),
                            orders.getQuantityOrdered(),
                            orders.getTotalAmount(),
                            orders.getStatus()
                    ))
                    .collect(Collectors.toList());
        }

        return dtoList;
    }

}
