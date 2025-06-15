package com.juaracoding.smartpro_rest_api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juaracoding.smartpro_rest_api.handler.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalResponse {
    // Success responses
    public static ResponseEntity<Object> dataCreated(HttpServletRequest request) {
        return new ResponseHandler().handleResponse("Data created!", HttpStatus.CREATED, null, null, request);
    }

    public static ResponseEntity<Object> dataSaveSuccess(HttpServletRequest request){
        return new ResponseHandler().handleResponse("SAVE SUCCESS !!", HttpStatus.CREATED,null,null,request);
    }

    public static ResponseEntity<Object> dataUpdated(HttpServletRequest request) {
        return new ResponseHandler().handleResponse("Data updated!", HttpStatus.OK, null, null, request);
    }

    public static ResponseEntity<Object> dataDeleted(HttpServletRequest request) {
        return new ResponseHandler().handleResponse("Data deleted!", HttpStatus.OK, null, null, request);
    }
    public static ResponseEntity<Object> dataFailedToSave(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse("FAILED TO SAVE !!", HttpStatus.INTERNAL_SERVER_ERROR,null,errorCode,request);
    }

    public static ResponseEntity<Object> dataFailedToChange(String errorCode, HttpServletRequest request) {
        return new ResponseHandler().handleResponse("DATA FAILED TO CHANGE", HttpStatus.INTERNAL_SERVER_ERROR, null, errorCode, request);
    }


    public static ResponseEntity<Object> dataFound(Object data, HttpServletRequest request) {
        return new ResponseHandler().handleResponse("Data found!", HttpStatus.OK, data, null, request);
    }

    public static ResponseEntity<Object> errorOccurred(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse("an Error Occurred", HttpStatus.INTERNAL_SERVER_ERROR,null,errorCode,request);
    }

    // Failed responses
    public static ResponseEntity<Object> exceptionCaught(String message, String errorCode, HttpServletRequest request) {
        return new ResponseHandler().handleResponse(message, HttpStatus.INTERNAL_SERVER_ERROR, null, errorCode, request);
    }

    public static ResponseEntity<Object> dataNotFound(String errorCode, HttpServletRequest request) {
        return new ResponseHandler().handleResponse("Data not found!", HttpStatus.NOT_FOUND,null, errorCode, request);
    }

    public static ResponseEntity<Object> objectIsNull(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse("OBJECT NULL !!", HttpStatus.BAD_REQUEST,null,errorCode,request);
    }

    public static ResponseEntity<Object> formatMustBeExcel(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse("FORMAT MUST BE EXCEL", HttpStatus.BAD_REQUEST,null,errorCode,request);
    }

    public static ResponseEntity<Object> emptyExcelFile(String errorCode , HttpServletRequest request){
        return new ResponseHandler().handleResponse("EXCEL FILE is Empty", HttpStatus.BAD_REQUEST,null,errorCode,request);
    }

    public static ResponseEntity<Object> uploadFileExcelSuccessful(HttpServletRequest request){
        return new ResponseHandler().handleResponse("UPLOAD EXCEL FILE SUCCESSFUL", HttpStatus.OK,null,null,request);
    }

    public static void manualResponse(HttpServletResponse response, ResponseEntity<Object> resObject){
        try{
            response.getWriter().write(convertObjectToJson(resObject.getBody()));
            response.setStatus(resObject.getStatusCodeValue());
        }catch (Exception e){
        }
    }

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if(object == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static ResponseEntity<Object> bodyParamRequestNull(String message, String errorCode, HttpServletRequest request) {
        return new ResponseHandler().handleResponse(message, HttpStatus.BAD_REQUEST, null, null, request);
    }
}
