package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.dto.validation.LoginDTO;
import com.juaracoding.smartpro_rest_api.model.Staff;
import com.juaracoding.smartpro_rest_api.repo.StaffRepo;
import com.juaracoding.smartpro_rest_api.security.BCryptImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AuthService implements UserDetailsService {

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

    // override functions
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Staff> opStaff = staffRepo.findByUsername(username);
        if (opStaff.isEmpty()) {
            throw new UsernameNotFoundException("Username not found!");
        }

        Staff existing = opStaff.get();
        return new User(existing.getUsername(), existing.getPassword(), existing.getAuthorities());
    }

    // additional functions
    public Staff parseDto(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO,Staff.class);
    }
}
