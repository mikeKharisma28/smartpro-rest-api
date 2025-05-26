package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DivisionRepo extends JpaRepository<Division, Long> {
    public Page<Division> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    public Page<Division> findByDeskripsiContainsIgnoreCase(String nama, Pageable pageable);

    public List<Division> findByNamaContainsIgnoreCase(String nama);
    public List<Division> findByDeskripsiContainsIgnoreCase(String nama);
}