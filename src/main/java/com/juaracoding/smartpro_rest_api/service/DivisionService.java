package com.juaracoding.smartpro_rest_api.service;

import com.juaracoding.smartpro_rest_api.core.IReport;
import com.juaracoding.smartpro_rest_api.core.IService;
import com.juaracoding.smartpro_rest_api.dto.report.RepDivisionDTO;
import com.juaracoding.smartpro_rest_api.dto.response.ResDivisionDTO;
import com.juaracoding.smartpro_rest_api.dto.validation.DivisionDTO;
import com.juaracoding.smartpro_rest_api.handler.ResponseHandler;
import com.juaracoding.smartpro_rest_api.model.Division;
import com.juaracoding.smartpro_rest_api.repo.DivisionRepo;
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
public class DivisionService implements IService<Division>, IReport<Division> {

    @Autowired
    private DivisionRepo divisionRepo;

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
    public ResponseEntity<Object> save(Division division, HttpServletRequest request) {
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(division == null){
                return new ResponseHandler().handleResponse("Object Null !!", HttpStatus.BAD_REQUEST,null,"AUT01FV001",request);
            }
            division.setCreatedBy(Long.parseLong(m.get("staffId").toString()));
            divisionRepo.save(division);
        }catch (Exception e){
            return GlobalResponse.dataFailedToSave("AUT01FE001",request);
        }
        return GlobalResponse.dataSaveSuccess(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Division division, HttpServletRequest request) {
        Map<String,Object> m = GlobalFunction.extractToken(request);
        try{
            if(id == null){
                return GlobalResponse.objectIsNull("AUT01FV011",request);
            }
            if(division == null){
                return GlobalResponse.objectIsNull("AUT01FV012",request);
            }
            Optional<Division> opDivision = divisionRepo.findById(id);
            if(!opDivision.isPresent()){
                return GlobalResponse.dataNotFound("AUT01FV013",request);
            }
            Division divisionDB = opDivision.get();
            divisionDB.setName(division.getName());
            divisionDB.setUpdatedBy(Long.parseLong(m.get("staffId").toString()));

        }catch (Exception e){
            return GlobalResponse.dataFailedToChange("AUT01FE011",request);
        }
        return GlobalResponse.dataUpdated(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Division> page = null;
        List<Division> list = null;
        List<RepDivisionDTO> divisionDTO = null;
        Map<String,Object> data = null;
        try {
            page = divisionRepo.findAll(pageable);
            if(page.isEmpty()){
                return GlobalResponse.dataNotFound("AUT01FV031",request);
            }
            divisionDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(divisionDTO,page,"id","");
        }catch (Exception e){
            return GlobalResponse.errorOccurred("AUT01FE031",request);
        }
        return GlobalResponse.dataFound(divisionDTO,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        ResDivisionDTO resDivisionDTO = null;
        try{
            if(id==null){
                return GlobalResponse.objectIsNull("AUT01FV041",request);

            }
            Optional<Division> opUser = divisionRepo.findById(id);
            if(!opUser.isPresent()){
                return GlobalResponse.dataNotFound("AUT01FV042",request);
            }
            Division divisionDB = opUser.get();
            resDivisionDTO = mapToDTO(divisionDB);
        }catch (Exception e){
            return GlobalResponse.errorOccurred("AUT01FE041",request);
        }

        return GlobalResponse.dataFound(resDivisionDTO,request);
    }


    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Division> page = null;
        List<RepDivisionDTO> divisionDTO = null;
        Map<String,Object> data = null;
        try {
            switch (columnName){
                case "name":page = divisionRepo.findByNameContainsIgnoreCase(value, pageable);break;
                default:page = divisionRepo.findAll(pageable);
            }
            if(page.isEmpty()){
                return GlobalResponse.dataNotFound("AUT01FV051",request);
            }
            divisionDTO = mapToDTO(page.getContent());
            data = tp.transformPagination(divisionDTO,page,columnName,value);
        }catch (Exception e){
            return GlobalResponse.errorOccurred("AUT01FE051",request);
        }
        return GlobalResponse.dataFound(divisionDTO,request);
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Division> listDivision = null;
        try {
            switch (column){
                case "name":listDivision = divisionRepo.findByNameContainsIgnoreCase(value);break;
                default:listDivision = divisionRepo.findAll();break;
            }
            if(listDivision.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataNotFound("AUT01FV071",request));
                return;
            }
            List<RepDivisionDTO> divisionDTO = mapToDTO(listDivision);

            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=user_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();//user_12-05-2025_18:22:33
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);

            Map<String,Object> map = GlobalFunction.convertClassToMap(new RepDivisionDTO());
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
            int intListDTOSize = divisionDTO.size();
            String [][] strBody = new String[intListDTOSize][intListTemp];
            for (int i = 0; i < intListDTOSize; i++) {
                map = GlobalFunction.convertClassToMap(divisionDTO.get(i));
                for (int j = 0; j < intListTemp; j++) {
                    strBody[i][j] = String.valueOf(map.get(loopDataArr[j]));
                }
            }
            new ExcelWriter(strBody,headerArr,"sheet-1",response);
        }catch (Exception e){
            GlobalResponse.
                    manualResponse(response,GlobalResponse.errorOccurred("AUT01FE071",request));
            return;
        }
    }

    @Override
    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Division> listDivision = null;
        try {
            switch (column){
                case "name":listDivision = divisionRepo.findByNameContainsIgnoreCase(value);break;
                default:listDivision = divisionRepo.findAll();break;
            }
            if(listDivision.isEmpty()){
                GlobalResponse.
                        manualResponse(response,GlobalResponse.dataNotFound("AUT01FV081",request));
                return;
            }
            List<RepDivisionDTO> divisionDTO = mapToDTO(listDivision);
            int intRepUserDTOSize = divisionDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();
            Map<String,Object> mapColumnName = GlobalFunction.convertClassToMap(new RepDivisionDTO());
            List<String> listTemp = new ArrayList<>();
            List<String> listHelper = new ArrayList<>();
            for (Map.Entry<String,Object> m:mapColumnName.entrySet()) {
                listTemp.add(GlobalFunction.camelToStandard(m.getKey()));
                listHelper.add(m.getKey());
            }

            Map<String,Object> mapTemp = null;
            List<Map<String,Object>> listMap = new ArrayList<>();
            for (int i = 0; i < intRepUserDTOSize; i++) {
                mapTemp = GlobalFunction.convertClassToMap(divisionDTO.get(i));
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
            pdfGenerator.htmlToPdf(strHtml,"division",response);
        }catch (Exception e){
            GlobalResponse.
                    manualResponse(response,GlobalResponse.errorOccurred("AUT01FE081",request));
            return;
        }
    }

    /** additional function */

    public Division mapToDivision(DivisionDTO divisionDTO){
        return modelMapper.map(divisionDTO, Division.class);
    }

    public List<RepDivisionDTO> mapToDTO(List<Division> listDivision){
        return modelMapper.map(listDivision,new TypeToken<List<RepDivisionDTO>>(){}.getType());
    }

    public ResDivisionDTO mapToDTO(Division division){
        return modelMapper.map(division,ResDivisionDTO.class);
    }

}
