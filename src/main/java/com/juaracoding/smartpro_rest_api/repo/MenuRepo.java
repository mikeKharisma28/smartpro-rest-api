package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepo extends JpaRepository<Menu, Long> {
    Optional<Menu> findById(Long id);
    Optional<Menu> findByName(String name);

    Page<Menu> findByNameContainsIgnoreCase(String name, Pageable pageable);
    Page<Menu> findByPathContainsIgnoreCase(String path, Pageable pageable);

//    List<Menu> findByNameContainsIgnoreCase(String name);
//    List<Menu> findByUrlContainsIgnoreCase(String url);

}