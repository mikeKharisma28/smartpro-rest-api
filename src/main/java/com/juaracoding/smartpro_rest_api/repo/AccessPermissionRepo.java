package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.AccessPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessPermissionRepo extends JpaRepository<AccessPermission, Long> {
}
