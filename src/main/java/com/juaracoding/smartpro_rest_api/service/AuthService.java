package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.dto.validation.LoginDTO;
import com.juaracoding.smartpro_rest_api.model.Staff;
import com.juaracoding.smartpro_rest_api.repo.StaffRepo;
import com.juaracoding.smartpro_rest_api.security.BCryptImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> login(Staff paramStaff, HttpServletRequest request) {
        Map<String, Object> bodyResponse = new HashMap<>();
        Staff existing = null;
        try {
            Optional<Staff> optionalStaff = staffRepo.findByUsername(paramStaff.getUsername());
            if(optionalStaff.isEmpty()) {
                return null;
            }

            existing = optionalStaff.get();

            String password = existing.getUsername() + paramStaff.getPassword();
            if (!BCryptImpl.verifyHash(password, existing.getPassword())) {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }

        return null;
    }

    // additional functions
    public Staff parseDto(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO,Staff.class);
    }
}
