package com.juaracoding.smartpro_rest_api.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IApproval<T> {
    public ResponseEntity<Object> approve(String no, T t, HttpServletRequest request);
    public ResponseEntity<Object> reject(String no, T t, HttpServletRequest request);
    public ResponseEntity<Object> findByNo(String no, HttpServletRequest request);
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request);
}
