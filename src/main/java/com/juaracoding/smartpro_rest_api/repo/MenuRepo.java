package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepo extends JpaRepository<Menu, Long> {

    Page<Menu> findById(Long id, Pageable pageable);
    Page<Menu> findByNameContainsIgnoreCase(String name, Pageable pageable);
    Page<Menu> findByUrlContainsIgnoreCase(String url, Pageable pageable);

    List<Menu> findByParentId(Long parentId);
    List<Menu> findByNameContainsIgnoreCase(String name);
    List<Menu> findByUrlContainsIgnoreCase(String url);

}