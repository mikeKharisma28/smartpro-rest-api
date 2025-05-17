package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Division;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DivisionRepo extends JpaRepository<Division, Long> {
}
