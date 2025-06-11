package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.core.IReport;
import com.juaracoding.smartpro_rest_api.core.IService;
import com.juaracoding.smartpro_rest_api.dto.report.RepRoleDTO;
import com.juaracoding.smartpro_rest_api.dto.response.ResRoleDTO;
import com.juaracoding.smartpro_rest_api.dto.validation.RoleDTO;
import com.juaracoding.smartpro_rest_api.handler.ResponseHandler;
import com.juaracoding.smartpro_rest_api.model.Role;
import com.juaracoding.smartpro_rest_api.repo.RoleRepo;
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
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class RoleService implements IService<Role>, IReport<Role> {

    @Autowired
    private RoleRepo roleRepo;

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
    public ResponseEntity<Object> save(Role role, HttpServletRequest request) {
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(role == null){
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST,null,"AUT03FV001",request);
            }
            role.setCreatedBy(Long.parseLong(m.get("staffId").toString()));
            roleRepo.save(role);
        }catch (Exception e){
            return GlobalResponse.dataFailedToSave("AUT03FE001",request);
        }
        return GlobalResponse.dataSaveSuccess(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Role role, HttpServletRequest request) {
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(id == null){
                return GlobalResponse.objectIsNull("AUT03FV011",request);
            }
            if(role == null){
                return GlobalResponse.objectIsNull("AUT03FV012",request);
            }
            Optional<Role> opRole = roleRepo.findById(id);
            if(!opRole.isPresent()){
                return GlobalResponse.dataNotFound("AUT03FV013",request);
            }
            Role roleDB = opRole.get();
            roleDB.setName(role.getName());
            roleDB.setMenus(role.getMenus());
            roleDB.setUpdatedBy(Long.parseLong(m.get("staffId").toString()));

        }catch (Exception e){
            return GlobalResponse.dataFailedToChange("AUT03FE011",request);
        }
        return GlobalResponse.dataUpdated(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Role> page = null;
        List<Role> list = null;
        List<RepRoleDTO> roleDTO = null;
        Map<String,Object> data = null;
        try {
            page = roleRepo.findAll(pageable);
            if(page.isEmpty()){
                return GlobalResponse.dataNotFound("AUT03FV031",request);
            }
            roleDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(roleDTO,page,"id","");
        }catch (Exception e){
            return GlobalResponse.errorOccurred("AUT03FE031",request);
        }
        return GlobalResponse.dataFound(roleDTO,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        ResRoleDTO resRoleDTO = null;
        try{
            if(id==null){
                return GlobalResponse.objectIsNull("AUT03FV041",request);

            }
            Optional<Role> opUser = roleRepo.findById(id);
            if(!opUser.isPresent()){
                return GlobalResponse.dataNotFound("AUT03FV042",request);
            }
            Role roleDB = opUser.get();
            resRoleDTO = mapToDTO(roleDB);
        }catch (Exception e){
            return GlobalResponse.errorOccurred("AUT03FE041",request);
        }

        return GlobalResponse.dataFound(resRoleDTO,request);
    }


    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Role> page = null;
        List<RepRoleDTO> roleDTO = null;
        Map<String,Object> data = null;
        try {
            switch (columnName){
                case "name":page = roleRepo.findByNameContainsIgnoreCase(value,pageable);break;
                default:page = roleRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return GlobalResponse.dataNotFound("AUT03FV051",request);
            }
            roleDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(roleDTO,page,columnName,value);
        }catch (Exception e){
            return GlobalResponse.errorOccurred("AUT03FE051",request);
        }
        return GlobalResponse.dataFound(roleDTO,request);
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Role> listRole = null;
        try {
            switch (column){
                case "name":listRole = roleRepo.findByNameContainsIgnoreCase(value);break;
                default:listRole = roleRepo.findAll();break;
            }
            if(listRole.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataNotFound("AUT03FV071",request));
                return;
            }
            List<RepRoleDTO> roleDTO = mapToDTO(listRole);

            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=user_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();//user_12-05-2025_18:22:33
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String,Object> map = GlobalFunction.convertClassToMap(new RepRoleDTO());
            List<String> listTemp = new ArrayList<>();
            for (Map.Entry<String,Object> entry : map.entrySet()) {
                listTemp.add(entry.getKey());
            }
            int intListTemp = listTemp.size();
            String [] headerArr = new String[intListTemp];//kolom judul di excel
            String [] loopDataArr = new String[intListTemp];//kolom judul java reflection

            for (int i = 0; i < intListTemp; i++) {
                headerArr[i] = GlobalFunction.camelToStandard(listTemp.get(i));
                loopDataArr[i] = listTemp.get(i);
            }
            int intListDTOSize = roleDTO.size();
            String [][] strBody = new String[intListDTOSize][intListTemp];
            for (int i = 0; i < intListDTOSize; i++) {
                map = GlobalFunction.convertClassToMap(roleDTO.get(i));
                for (int j = 0; j < intListTemp; j++) {
                    strBody[i][j] = String.valueOf(map.get(loopDataArr[j]));
                }
            }
            new ExcelWriter(strBody,headerArr,"sheet-1",response);
        }catch (Exception e){
            GlobalResponse.
                    manualResponse(response,GlobalResponse.errorOccurred("AUT03FE071",request));
            return;
        }
    }

    @Override
    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Role> listRole = null;
        try {
            switch (column){
                case "name":listRole = roleRepo.findByNameContainsIgnoreCase(value);break;
                default:listRole = roleRepo.findAll();break;
            }
            if(listRole.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataNotFound("AUT03FV081",request));
                return;
            }
            List<RepRoleDTO> roleDTO = mapToDTO(listRole);
            int intRepUserDTOSize = roleDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RepRoleDTO());
            List<String> listTemp = new ArrayList<>();
            List<String> listHelper = new ArrayList<>();
            for (Map.Entry<String,Object> m:mapColumnName.entrySet()) {
                listTemp.add(GlobalFunction.camelToStandard(m.getKey()));
                listHelper.add(m.getKey());
            }

            Map<String,Object> mapTemp = null;
            List<Map<String,Object>> listMap = new ArrayList<>();
            for (int i = 0; i < intRepUserDTOSize; i++) {
                mapTemp = GlobalFunction.convertClassToMap(roleDTO.get(i));
                listMap.add(mapTemp);
            }

            mapResponse.put("title","REPORT DATA MENU");
            mapResponse.put("listKolom",listTemp);
            mapResponse.put("listHelper",listHelper);
            mapResponse.put("listContent",listMap);
            mapResponse.put("totalData",intRepUserDTOSize);
            mapResponse.put("username","Reywa");

            context.setVariables(mapResponse);
            strHtml = springTemplateEngine.process("global-report",context);
            pdfGenerator.htmlToPdf(strHtml,"role",response);
        }catch (Exception e){
            GlobalResponse.
                    manualResponse(response,GlobalResponse.errorOccurred("AUT03FE081",request));
            return;
        }
    }

    /** additional function */

    public Role mapToRole(RoleDTO roleDTO){
        return modelMapper.map(roleDTO, Role.class);
    }

    public List<RepRoleDTO> mapToDTO(List<Role> listRole){
        return modelMapper.map(listRole,new TypeToken<List<RepRoleDTO>>(){}.getType());
    }

    public ResRoleDTO mapToDTO(Role role){
        return modelMapper.map(role,ResRoleDTO.class);
    }

}
