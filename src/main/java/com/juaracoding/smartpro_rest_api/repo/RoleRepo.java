package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
