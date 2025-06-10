package com.juaracoding.smartpro_rest_api.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IReport<G> {
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response);
    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response);
}
