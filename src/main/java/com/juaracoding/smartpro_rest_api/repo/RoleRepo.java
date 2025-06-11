package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Long> {

    public Page<Role> findByNameContainsIgnoreCase(String name, Pageable pageable);

    public List<Role> findByNameContainsIgnoreCase(String name);

}
