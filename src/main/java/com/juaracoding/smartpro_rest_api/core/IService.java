package com.juaracoding.smartpro_rest_api.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IService<T> {
    public ResponseEntity<Object> save(T t, HttpServletRequest request);
    public ResponseEntity<Object> update(Long id, T t, HttpServletRequest request);
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request);
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request);
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request);
}
