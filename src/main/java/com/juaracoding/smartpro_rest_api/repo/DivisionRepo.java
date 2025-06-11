package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Division;
import com.juaracoding.smartpro_rest_api.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DivisionRepo extends JpaRepository<Division, Long> {
    Optional<Division> findById(Long id);

    Page<Division> findByNameContainsIgnoreCase(String name, Pageable pageable);

    List<Division> findByNameContainsIgnoreCase(String name);
}