package com.example.Clientdemo.ClientDetails_test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
	public static Map<String, Object> generateResponse(Object responseObj, String message, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", responseObj);
		map.put("message", message);
		map.put("status", status);

		return map;
	}
}
