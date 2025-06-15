package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.core.IReport;
import com.juaracoding.smartpro_rest_api.core.IService;
import com.juaracoding.smartpro_rest_api.dto.report.RepMenuDTO;
import com.juaracoding.smartpro_rest_api.dto.response.ResMenuDTO;
import com.juaracoding.smartpro_rest_api.dto.validation.MenuDTO;
import com.juaracoding.smartpro_rest_api.handler.ResponseHandler;
import com.juaracoding.smartpro_rest_api.model.Menu;
import com.juaracoding.smartpro_rest_api.repo.MenuRepo;
import com.juaracoding.smartpro_rest_api.repo.MenuRepo;
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

/**
 * Author: Reynaldi
 * Created date: 2025-06-11
 * Edited by: Michael
 * Edited date: 2025-06-16
 */

@Service
@Transactional
public class MenuService implements IService<Menu>, IReport<Menu> {

    @Autowired
    private MenuRepo menuRepo;

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
    public ResponseEntity<Object> save(Menu menu, HttpServletRequest request) {
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(menu == null){
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST,null,"AUT02FV001",request);
            }
            menu.setCreatedBy(Long.parseLong(m.get("staffId").toString()));
            menuRepo.save(menu);
        }catch (Exception e){
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT02FE001",request);
        }
        return GlobalResponse.dataCreated(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Menu menu, HttpServletRequest request) {
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(id == null){
                return GlobalResponse.bodyParamRequestNull("Param id is not provided!", "AUT02FV011", request);
            }
            if(menu == null){
                return GlobalResponse.bodyParamRequestNull("Menu object is null!", "AUT02FV012", request);
            }
            Optional<Menu> opMenu = menuRepo.findById(id);
            if(!opMenu.isPresent()){
                return GlobalResponse.dataNotFound("AUT02FV013",request);
            }
            Menu menuDB = opMenu.get();
            menuDB.setName(menu.getName());
            menuDB.setPath(menu.getPath());
            menuDB.setUpdatedBy(Long.parseLong(m.get("staffId").toString()));

        }catch (Exception e){
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT02FE011",request);
        }
        return GlobalResponse.dataUpdated(request);
    }

    /***
     * Edited by : Michael
     * Edited date : 2025-06-16
     * @param pageable
     * @param request
     * @return
     */
    
    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Menu> page = null;
        List<Menu> list = null;
        List<RepMenuDTO> menuDTO = null;
        Map<String,Object> data = null;
        try {
            page = menuRepo.findAll(pageable);
            if(page.isEmpty()){
                return GlobalResponse.dataNotFound("AUT02FV031", request);
            }
            menuDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(menuDTO, page, "id", "");
        }catch (Exception e){
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT02FE031", request);
        }
        return GlobalResponse.dataFound(data, request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        ResMenuDTO resMenuDTO = null;
        try{
            if(id==null){
                return GlobalResponse.bodyParamRequestNull("Param id is not provided!", "AUT02FV041", request);

            }
            Optional<Menu> opUser = menuRepo.findById(id);
            if(!opUser.isPresent()){
                return GlobalResponse.dataNotFound("AUT02FV042",request);
            }
            Menu menuDB = opUser.get();
            resMenuDTO = mapToDTO(menuDB);
        }catch (Exception e){
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT02FE041",request);
        }

        return GlobalResponse.dataFound(resMenuDTO,request);
    }


    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Menu> page = null;
        List<RepMenuDTO> menuDTO = null;
        Map<String,Object> data = null;
        try {
            switch (columnName){
                case "name":
                    page = menuRepo.findByNameContainsIgnoreCase(value,pageable);
                    break;
                case "path":
                    page = menuRepo.findByPathContainsIgnoreCase(value, pageable);
                    break;
                default:page = menuRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return GlobalResponse.dataNotFound("AUT02FV051",request);
            }
            menuDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(menuDTO,page,columnName,value);
        }catch (Exception e){
            return GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT02FE051",request);
        }
        return GlobalResponse.dataFound(menuDTO,request);
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Menu> listMenu = null;
        try {
            switch (column){
                case "name":
                    listMenu = menuRepo.findByNameContainsIgnoreCase(value);
                    break;
                case "path" : 
                    listMenu = menuRepo.findByPathContainsIgnoreCase(value);
                    break;
                default:
                    listMenu = menuRepo.findAll();
                    break;
            }
            if(listMenu.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataNotFound("AUT02FV071",request));
                return;
            }
            List<RepMenuDTO> menuDTO = mapToDTO(listMenu);

            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=user_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();//user_12-05-2025_18:22:33
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String,Object> map = GlobalFunction.convertClassToMap(new RepMenuDTO());
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
            int intListDTOSize = menuDTO.size();
            String [][] strBody = new String[intListDTOSize][intListTemp];
            for (int i = 0; i < intListDTOSize; i++) {
                map = GlobalFunction.convertClassToMap(menuDTO.get(i));
                for (int j = 0; j < intListTemp; j++) {
                    strBody[i][j] = String.valueOf(map.get(loopDataArr[j]));
                }
            }
            new ExcelWriter(strBody,headerArr,"sheet-1",response);
        } catch (Exception e){
            GlobalResponse.
                    manualResponse(response,GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT02FE071",request));
            return;
        }
    }

    @Override
    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Menu> listMenu = null;
        try {
            switch (column){
                case "name":listMenu = menuRepo.findByNameContainsIgnoreCase(value);break;
                case "path" : listMenu = menuRepo.findByPathContainsIgnoreCase(value);break;
                default:listMenu = menuRepo.findAll();break;
            }
            if(listMenu.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataNotFound("AUT02FV081",request));
                return;
            }
            List<RepMenuDTO> menuDTO = mapToDTO(listMenu);
            int intRepUserDTOSize = menuDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RepMenuDTO());
            List<String> listTemp = new ArrayList<>();
            List<String> listHelper = new ArrayList<>();
            for (Map.Entry<String,Object> m:mapColumnName.entrySet()) {
                listTemp.add(GlobalFunction.camelToStandard(m.getKey()));
                listHelper.add(m.getKey());
            }

            Map<String,Object> mapTemp = null;
            List<Map<String,Object>> listMap = new ArrayList<>();
            for (int i = 0; i < intRepUserDTOSize; i++) {
                mapTemp = GlobalFunction.convertClassToMap(menuDTO.get(i));
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
            pdfGenerator.htmlToPdf(strHtml,"menu",response);
        }catch (Exception e){
            GlobalResponse.
                    manualResponse(response,GlobalResponse.exceptionCaught("Error: " + e.getMessage(), "AUT02FE081",request));
            return;
        }
    }

    /** additional function */

    public Menu mapToMenu(MenuDTO menuDTO){
        return modelMapper.map(menuDTO, Menu.class);
    }

    public List<RepMenuDTO> mapToDTO(List<Menu> listMenu){
        return modelMapper.map(listMenu,new TypeToken<List<RepMenuDTO>>(){}.getType());
    }

    public ResMenuDTO mapToDTO(Menu menu){
        return modelMapper.map(menu,ResMenuDTO.class);
    }

}
