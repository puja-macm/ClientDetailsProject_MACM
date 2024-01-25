package com.example.Clientdemo.ClientDetails_test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(Object responseObj, String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", responseObj);
        map.put("message", message);
        map.put("status", status.value());


        return new ResponseEntity<>(map, status);
    }
}
