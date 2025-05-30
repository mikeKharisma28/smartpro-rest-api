package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepo extends JpaRepository<Staff, Long> {

    Optional<Staff> findById(Long id);
    Optional<Staff> findByUsername(String username);
    Optional<Staff> findByUsernameOrIdOrPhoneNumber(String username, Long id, String phoneNumber);

    Page<Staff> findByFullNameContainsIgnoreCase(String fullName, Pageable pageable);
    Page<Staff> findByPhoneNumberContainsIgnoreCase(String phoneNumber, Pageable pageable);
    Page<Staff> findByUsernameContainsIgnoreCase(String username, Pageable pageable);

    List<Staff> findByFullNameContainsIgnoreCase(String fullName);
    List<Staff> findByPhoneNumberContainsIgnoreCase(String phoneNumber);
    List<Staff> findByUsernameContainsIgnoreCase(String username);
}