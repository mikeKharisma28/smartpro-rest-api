package com.juaracoding.smartpro_rest_api.repo;

import com.juaracoding.smartpro_rest_api.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepo extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmail(String email);
    Optional<Staff> findByStaffname(String username);
    Optional<Staff> findByStaffnameAndIsRegistered(String username, Boolean valid);
    Optional<Staff> findByStaffnameOrEmailOrNoHp(String username,String email,String noHp);
    Optional<Staff> findByStaffnameOrEmailOrNoHpAndIsRegistered(String username, String email, String noHp, Boolean valid);

    public Page<Staff> findByNamaLengkapContainsIgnoreCase(String nama, Pageable pageable);
    public Page<Staff> findByAlamatContainsIgnoreCase(String nama, Pageable pageable);
    public Page<Staff> findByEmailContainsIgnoreCase(String nama, Pageable pageable);
    public Page<Staff> findByStaffnameContainsIgnoreCase(String nama, Pageable pageable);
    public Page<Staff> findByNoHpContainsIgnoreCase(String nama, Pageable pageable);
//    public Page<Staff> findByTanggalLahirContainsIgnoreCase(String nama, Pageable pageable);

    public List<Staff> findByNamaLengkapContainsIgnoreCase(String nama);
    public List<Staff> findByAlamatContainsIgnoreCase(String nama);
    public List<Staff> findByEmailContainsIgnoreCase(String nama);
    public List<Staff> findByStaffnameContainsIgnoreCase(String nama);
    public List<Staff> findByNoHpContainsIgnoreCase(String nama);
//    public List<Staff> findByTanggalLahirContainsIgnoreCase(String nama);

}