package com.juaracoding.smartpro_rest_api.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IReport<G> {
//    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request);
//    public List<G> convertListWorkbookToListEntity(List<Map<String, String>> workbookData, Long userId);
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response);
    public void generateToPdf(String column, String value, HttpServletRequest request, HttpServletResponse response);
}
