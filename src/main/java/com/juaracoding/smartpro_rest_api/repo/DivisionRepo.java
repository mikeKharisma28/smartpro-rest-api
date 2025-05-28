package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DivisionRepo extends JpaRepository<Division, Long> {
//    public Page<Division> findById(Long id, Pageable pageable);
//    public Page<Division> findByNameContainsIgnoreCase(String name, Pageable pageable);
//
//    public List<Division> findByIdContains(Long id);
//    public List<Division> findByNameContainsIgnoreCase(String name);
}