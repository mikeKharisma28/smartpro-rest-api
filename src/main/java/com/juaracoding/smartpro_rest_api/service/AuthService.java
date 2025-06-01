package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.config.JwtConfig;
import com.juaracoding.smartpro_rest_api.dto.MenuLoginDTO;
import com.juaracoding.smartpro_rest_api.dto.validation.LoginDTO;
import com.juaracoding.smartpro_rest_api.handler.ResponseHandler;
import com.juaracoding.smartpro_rest_api.model.Menu;
import com.juaracoding.smartpro_rest_api.model.Staff;
import com.juaracoding.smartpro_rest_api.repo.StaffRepo;
import com.juaracoding.smartpro_rest_api.security.BCryptImpl;
import com.juaracoding.smartpro_rest_api.security.Crypto;
import com.juaracoding.smartpro_rest_api.security.JwtUtility;
import com.juaracoding.smartpro_rest_api.util.GlobalResponse;
import com.juaracoding.smartpro_rest_api.util.LoggingFile;
import com.juaracoding.smartpro_rest_api.util.TransformationDataMenu;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AuthService implements UserDetailsService {

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtility jwtUtility;

    public ResponseEntity<Object> login(Staff paramStaff, HttpServletRequest request) {
        Map<String, Object> bodyResponse = new HashMap<>();
        Staff existing = null;
        try {
            Optional<Staff> optionalStaff = staffRepo.findByUsername(paramStaff.getUsername());
            if (optionalStaff.isEmpty()) {
                return GlobalResponse.dataNotFound("", request);
            }

            existing = optionalStaff.get();

            String password = existing.getUsername() + paramStaff.getPassword();
            if (!BCryptImpl.verifyHash(password, existing.getPassword())) {
                return new ResponseHandler().handleResponse("Wrong password!", HttpStatus.BAD_REQUEST, null, null, request);
            }
        } catch (Exception e) {
            LoggingFile.logException(AuthService.class.toString(), "login(Staff paramStaff, HttpServletRequest request)", e);
            return GlobalResponse.exceptionCaught("Failed to log in!", "", request);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("staffId", existing.getId());
        response.put("username", existing.getUsername());
        response.put("phoneNumber", existing.getPhoneNumber());
        response.put("fullName", existing.getFullName());

        List<MenuLoginDTO> menuList = parseMenuDto(existing.getRole().getMenus());
        String token = jwtUtility.doGenerateToken(response, existing.getUsername());

        response.put("menu", new TransformationDataMenu().doTransformAksesMenuLogin(menuList));
        response.put("token", token);

        return new ResponseHandler().handleResponse("Logged in!", HttpStatus.OK, response, null, request);
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
    public Staff parseToModel(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, Staff.class);
    }

    public List<MenuLoginDTO> parseMenuDto(List<Menu> listMenu) {
        List<MenuLoginDTO> listMenuDto = new ArrayList<>();
        for (Menu menu : listMenu) {
            MenuLoginDTO menuLoginDTO = new MenuLoginDTO();
            menuLoginDTO.setName(menu.getName());
            menuLoginDTO.setPath(menu.getPath());
            menuLoginDTO.setParentMenuName(menu.getParent() != null ? menu.getParent().getName() : "-");
            listMenuDto.add(menuLoginDTO);
        }

        return listMenuDto;
    }
}
