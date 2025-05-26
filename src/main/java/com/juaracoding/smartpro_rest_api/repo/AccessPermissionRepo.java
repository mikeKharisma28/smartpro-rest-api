package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.AccessPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessPermissionRepo extends JpaRepository<AccessPermission, Long> {

    public Page<AccessPermission> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    public Page<AccessPermission> findByDeskripsiContainsIgnoreCase(String nama, Pageable pageable);

    public List<AccessPermission> findByNamaContainsIgnoreCase(String nama);
    public List<AccessPermission> findByDeskripsiContainsIgnoreCase(String nama);
}