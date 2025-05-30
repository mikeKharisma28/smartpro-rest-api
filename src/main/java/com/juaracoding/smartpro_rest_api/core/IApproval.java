package com.juaracoding.smartpro_rest_api.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;

public interface IApproval<T> {
    public ResponseEntity<Object> approve(String no, T t, HttpServletRequest request);
    public ResponseEntity<Object> reject(String no, T t, HttpServletRequest request);
    public ResponseEntity<Object> findByNo(String no, HttpServletRequest request);
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request);
}
