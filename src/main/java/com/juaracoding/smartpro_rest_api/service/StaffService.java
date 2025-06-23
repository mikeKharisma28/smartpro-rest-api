package com.juaracoding.smartpro_rest_api.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.juaracoding.smartpro_rest_api.core.IReport;
import com.juaracoding.smartpro_rest_api.core.IService;
import com.juaracoding.smartpro_rest_api.dto.report.RepStaffDTO;
import com.juaracoding.smartpro_rest_api.dto.response.ResStaffDTO;
import com.juaracoding.smartpro_rest_api.dto.response.ResStaffProfileDTO;
import com.juaracoding.smartpro_rest_api.dto.validation.EditStaffDTO;
import com.juaracoding.smartpro_rest_api.handler.ResponseHandler;
import com.juaracoding.smartpro_rest_api.model.Staff;
import com.juaracoding.smartpro_rest_api.repo.StaffRepo;
import com.juaracoding.smartpro_rest_api.security.BCryptImpl;
import com.juaracoding.smartpro_rest_api.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Author: Reynaldi, 2025-06-11
 * Edited by: Michael, 2025-06-14
 * Bugs fixed:
 * - findAll function returns Map<String, Object> data instead of List<StaffListDTO> listDTO
 */

@Service
@Transactional
public class StaffService implements IService<Staff>, IReport<Staff> {

    @Autowired
    private Cloudinary cloudinary;
    public static final String BASE_URL_IMAGE = System.getProperty("user.dir") + "\\image-saved";
    private static Path rootPath;


    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination tp;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;

    private StringBuilder sBuild = new StringBuilder();

    @Override
    public ResponseEntity<Object> save(Staff staff, HttpServletRequest request) {
        Map<String, Object> m = GlobalFunction.extractToken(request);
        try {
            if (staff == null) {
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST, null, "AUT04FV001", request);
            }
            staff.setPassword(BCryptImpl.hash(staff.getUsername() + staff.getPassword()));
            staff.setCreatedBy(Long.parseLong(m.get("staffId").toString()));
            staffRepo.save(staff);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE001", request);
        }
        return GlobalResponse.dataCreated(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Staff staff, HttpServletRequest request) {
        Map<String, Object> m = GlobalFunction.extractToken(request);
        try {
            if (id == null) {
                return GlobalResponse.bodyParamRequestNull("Param id is not provided!", "AUT04FV011", request);
            }
            if (staff == null) {
                return GlobalResponse.bodyParamRequestNull("Staff object is null!", "AUT04FV012", request);
            }
            Optional<Staff> opStaff = staffRepo.findById(id);
            if (opStaff.isEmpty()) {
                return GlobalResponse.dataNotFound("AUT04FV013", request);
            }
            Staff staffDB = opStaff.get();
            staffDB.setPassword(BCryptImpl.hash(staff.getUsername() + staff.getPassword()));
            staffDB.setFullName(staff.getFullName());
            staffDB.setRole(staff.getRole());
            staffDB.setUsername(staff.getUsername());
            staffDB.setPhoneNumber(staff.getPhoneNumber());
            staffDB.setDivision(staff.getDivision());
            staffDB.setUpdatedBy(Long.parseLong(m.get("staffId").toString()));
            staffDB.setPhotoProfileUrl(staff.getPhotoProfileUrl());

        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE011", request);
        }
        return GlobalResponse.dataUpdated(request);
    }

    /***
     * Edited by : Michael, 2025-06-16
     * @param pageable
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Staff> page = null;
        List<Staff> list = null;
        List<RepStaffDTO> listDTO = null;
        Map<String, Object> data = null;
        try {
            page = staffRepo.findAll(pageable);
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("AUT04FV031", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, "id", "");
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE031", request);
        }
        return GlobalResponse.dataFound(data, request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        ResStaffDTO resStaffDTO = null;
        try {
            if (id == null) {
                return GlobalResponse.bodyParamRequestNull("Param id is not provided!", "AUT04FV041", request);

            }
            Optional<Staff> opUser = staffRepo.findById(id);
            if (opUser.isEmpty()) {
                return GlobalResponse.dataNotFound("AUT04FV042", request);
            }
            Staff staffDB = opUser.get();
            resStaffDTO = mapToDTO(staffDB);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE041", request);
        }

        return GlobalResponse.dataFound(resStaffDTO, request);
    }

    public ResponseEntity<Object> findProfileById(Long id, HttpServletRequest request) {
        ResStaffProfileDTO resStaffProfileDTO = null;
        try {
            if (id == null) {
                return GlobalResponse.bodyParamRequestNull("Param id is not provided!", "AUT04FV041", request);

            }
            Optional<Staff> opUser = staffRepo.findById(id);
            if (opUser.isEmpty()) {
                return GlobalResponse.dataNotFound("AUT04FV042", request);
            }
            Staff staffDB = opUser.get();
            resStaffProfileDTO = mapToProfileDTO(staffDB);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE041", request);
        }

        return GlobalResponse.dataFound(resStaffProfileDTO, request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Staff> page = null;
        List<RepStaffDTO> listDTO = null;
        Map<String, Object> data = null;
        try {
            switch (columnName) {
                case "full-name":
                    page = staffRepo.findByFullNameContainsIgnoreCase(value, pageable);
                    break;
                case "phone-number":
                    page = staffRepo.findByPhoneNumberContainsIgnoreCase(value, pageable);
                    break;
//                case "tanggal-lahir":page = staffRepo.findByTanggalLahirContainsIgnoreCase(value,pageable);break;
                case "username":
                    page = staffRepo.findByUsernameContainsIgnoreCase(value, pageable);
                    break;
                default:
                    page = staffRepo.findAll(pageable);
            }
            if (page.isEmpty()) {
                return GlobalResponse.dataNotFound("AUT04FV051", request);
            }
            listDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(listDTO, page, columnName, value);
        } catch (Exception e) {
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE051", request);
        }
        return GlobalResponse.dataFound(listDTO, request);
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Staff> listStaff = null;
        try {
            switch (column) {
                case "full-name":
                    listStaff = staffRepo.findByFullNameContainsIgnoreCase(value);
                    break;
                case "phone-number":
                    listStaff = staffRepo.findByPhoneNumberContainsIgnoreCase(value);
                    break;
//                case "tanggal-lahir":page = staffRepo.findByTanggalLahirContainsIgnoreCase(value,pageable);break;
                case "username":
                    listStaff = staffRepo.findByUsernameContainsIgnoreCase(value);
                    break;
                default:
                    listStaff = staffRepo.findAll();
                    break;
            }
            if (listStaff.isEmpty()) {
                GlobalResponse.
                        manualResponse(response, GlobalResponse.dataNotFound("AUT04FV071", request));
                return;
            }
            List<RepStaffDTO> listDTO = mapToDTO(listStaff);

            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=user_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();//user_12-05-2025_18:22:33
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String, Object> map = GlobalFunction.convertClassToMap(new RepStaffDTO());
            List<String> listTemp = new ArrayList<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                listTemp.add(entry.getKey());
            }
            int intListTemp = listTemp.size();
            String[] headerArr = new String[intListTemp];//kolom judul di excel
            String[] loopDataArr = new String[intListTemp];//kolom judul java reflection

            for (int i = 0; i < intListTemp; i++) {
                headerArr[i] = GlobalFunction.camelToStandard(listTemp.get(i));
                loopDataArr[i] = listTemp.get(i);
            }
            int intListDTOSize = listDTO.size();
            String[][] strBody = new String[intListDTOSize][intListTemp];
            for (int i = 0; i < intListDTOSize; i++) {
                map = GlobalFunction.convertClassToMap(listDTO.get(i));
                for (int j = 0; j < intListTemp; j++) {
                    strBody[i][j] = String.valueOf(map.get(loopDataArr[j]));
                }
            }
            new ExcelWriter(strBody, headerArr, "sheet-1", response);
        } catch (Exception e) {
            GlobalResponse.
                    manualResponse(response, GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE071", request));
            return;
        }
    }

    @Override
    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Staff> listStaff = null;
        try {
            switch (column) {
                case "full-name":
                    listStaff = staffRepo.findByFullNameContainsIgnoreCase(value);
                    break;
                case "phone-number":
                    listStaff = staffRepo.findByPhoneNumberContainsIgnoreCase(value);
                    break;
//                case "tanggal-lahir":page = staffRepo.findByTanggalLahirContainsIgnoreCase(value,pageable);break;
                case "username":
                    listStaff = staffRepo.findByUsernameContainsIgnoreCase(value);
                    break;
                default:
                    listStaff = staffRepo.findAll();
                    break;
            }
            if (listStaff.isEmpty()) {
                GlobalResponse.
                        manualResponse(response, GlobalResponse.dataNotFound("AUT04FV081", request));
                return;
            }
            List<RepStaffDTO> listDTO = mapToDTO(listStaff);
            int intRepUserDTOSize = listDTO.size();
            Map<String, Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String, Object> mapColumnName = GlobalFunction.convertClassToMap(new RepStaffDTO());
            List<String> listTemp = new ArrayList<>();
            List<String> listHelper = new ArrayList<>();
            for (Map.Entry<String, Object> m : mapColumnName.entrySet()) {
                listTemp.add(GlobalFunction.camelToStandard(m.getKey()));
                listHelper.add(m.getKey());
            }

            Map<String, Object> mapTemp = null;
            List<Map<String, Object>> listMap = new ArrayList<>();
            for (int i = 0; i < intRepUserDTOSize; i++) {
                mapTemp = GlobalFunction.convertClassToMap(listDTO.get(i));
                listMap.add(mapTemp);
            }

            mapResponse.put("title", "REPORT DATA MENU");
            mapResponse.put("listKolom", listTemp);
            mapResponse.put("listHelper", listHelper);
            mapResponse.put("listContent", listMap);
            mapResponse.put("totalData", intRepUserDTOSize);
            mapResponse.put("username", "Reywa");

            context.setVariables(mapResponse);
            strHtml = springTemplateEngine.process("global-report", context);
            pdfGenerator.htmlToPdf(strHtml, "staff", response);
        } catch (Exception e) {
            GlobalResponse.
                    manualResponse(response, GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT04FE081", request));
            return;
        }
    }

    /**
     * additional function
     */

    public Staff mapToUser(EditStaffDTO editStaffDTO) {
        return modelMapper.map(editStaffDTO, Staff.class);
    }

    public List<RepStaffDTO> mapToDTO(List<Staff> listStaff) {
        return modelMapper.map(listStaff, new TypeToken<List<RepStaffDTO>>() {
        }.getType());
    }

    public ResStaffDTO mapToDTO(Staff user) {
        return modelMapper.map(user, ResStaffDTO.class);
    }

    public ResStaffProfileDTO mapToProfileDTO(Staff user) {
        return modelMapper.map(user, ResStaffProfileDTO.class);
    }

    public void save(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Gagal Untuk menyimpan File kosong !!");
            }
            Path destinationFile = this.rootPath.resolve(Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootPath.toAbsolutePath())) {
                // This is a security check
                throw new IllegalArgumentException(
                        "Tidak Dapat menyimpan file diluar storage yang sudah ditetapkan !!");
            }
            Files.createDirectories(this.rootPath);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Failed to store file.", e);
        }
    }

    public ResponseEntity<Object> uploadImage(String username, MultipartFile file, HttpServletRequest request) {
        Map map;
        Map<String, Object> mapResponse;
        Optional<Staff> userOptional = staffRepo.findByUsername(username);
        if (userOptional.isEmpty()) {
            return GlobalResponse.dataNotFound("", request);
        }
        rootPath = Paths.get(BASE_URL_IMAGE + "/" + new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date()));
        String strPath = rootPath.toAbsolutePath().toString();
        String strPathImage = strPath + "\\" + file.getOriginalFilename();
        save(file);

        try {
            map = cloudinary.uploader().upload(strPathImage, ObjectUtils.asMap("public_id", file.getOriginalFilename()));
            Staff staffDB = userOptional.get();
            staffDB.setUpdatedBy(staffDB.getId());
            staffDB.setUpdatedDate(LocalDateTime.now());
            staffDB.setPhotoProfileUrl(map.get("secure_url").toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        Map<String, Object> m = new HashMap<>();
        m.put("url-img", map.get("secure_url").toString());
        return ResponseEntity.status(HttpStatus.OK).body(m);
//        return GlobalResponse.dataResponseObject(m,request);
    }


}
