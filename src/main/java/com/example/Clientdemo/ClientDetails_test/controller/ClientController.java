package com.example.Clientdemo.ClientDetails_test.controller;

import com.example.Clientdemo.ClientDetails_test.ResponseHandler;
import com.example.Clientdemo.ClientDetails_test.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping("macm/user/client/{id}")
	// @ResponseStatus(code = HttpStatus.OK, reason = "OK")
	public ResponseEntity<Object> getData(@PathVariable String id) {
		try {

			Map<String, String> clientData = clientService.getData(id);
			// return new ResponseEntity<>(clientData, HttpStatus.OK);
			if (clientData.isEmpty()) {
				return ResponseHandler.generateResponse("","No Content Found", HttpStatus.BAD_REQUEST);
			}

			return ResponseHandler.generateResponse(clientData,"Successfully retrieved data!", HttpStatus.OK);

		} catch (Exception ex) {
			// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			return ResponseHandler.generateResponse("","INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
