package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/***
 * Author: Reynaldi, 2025-06-11
 * Last updated date: 2025-06-17
 */

public interface RoleRepo extends JpaRepository<Role, Long> {

    public Page<Role> findByNameContainsIgnoreCase(String name, Pageable pageable);

    public List<Role> findByNameContainsIgnoreCase(String name);

    public Optional<Role> findTop1ByOrderByIdDesc();

}
