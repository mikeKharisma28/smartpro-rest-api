package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepo extends JpaRepository<Menu, Long> {
}
