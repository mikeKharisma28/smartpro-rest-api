package com.juaracoding.smartpro_rest_api.util;

import com.juaracoding.smartpro_rest_api.handler.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalResponse {
    // Success responses
    public static ResponseEntity<Object> dataCreated(HttpServletRequest request) {
        return new ResponseHandler().handleResponse("Data created!", HttpStatus.CREATED, null, null, request);
    }

    public static ResponseEntity<Object> dataUpdated(HttpServletRequest request) {
        return new ResponseHandler().handleResponse("Data updated!", HttpStatus.OK, null, null, request);
    }

    public static ResponseEntity<Object> dataDeleted(HttpServletRequest request) {
        return new ResponseHandler().handleResponse("Data deleted!", HttpStatus.OK, null, null, request);
    }

    public static ResponseEntity<Object> dataFound(Object data, HttpServletRequest request) {
        return new ResponseHandler().handleResponse("Data found!", HttpStatus.OK, data, null, request);
    }

    // Failed responses
    public static ResponseEntity<Object> exceptionCaught(String message, String errorCode, HttpServletRequest request) {
        return new ResponseHandler().handleResponse(message, HttpStatus.INTERNAL_SERVER_ERROR, null, errorCode, request);
    }

    public static ResponseEntity<Object> dataNotFound(String errorCode, HttpServletRequest request) {
        return new ResponseHandler().handleResponse("Data not found!", HttpStatus.NOT_FOUND,null, errorCode, request);
    }

    public static ResponseEntity<Object> bodyParamRequestNull(String message, String errorCode, HttpServletRequest request) {
        return new ResponseHandler().handleResponse(message, HttpStatus.BAD_REQUEST, null, null, request);
    }
}
