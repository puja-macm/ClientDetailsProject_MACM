package com.example.Clientdemo.ClientDetails_test.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clientdemo.ClientDetails_test.ResponseHandler;
import com.example.Clientdemo.ClientDetails_test.service.ClientService;

@RestController
@RequestMapping("macm/user")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping("/getClientDetails")
	public Map<String, Object> getDataForClient(@RequestParam String clientCode) throws SQLException {
		try {

			Map<String, String> clientData = clientService.getData(clientCode);
			
			if (clientData.isEmpty()) {
				return ResponseHandler.generateResponse(null, "No Content Found", 0);

			}

			return ResponseHandler.generateResponse(clientData, "Success", 0);

		} catch (Exception ex) {
			// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			return ResponseHandler.generateResponse("", "INTERNAL_SERVER_ERROR",500);

		}

	}
//This method is for getting partner details 
	@GetMapping("/getPartnerDetails")
	public Map<String, Object> getDataForPartner(@RequestParam String partnerCode) throws SQLException {
		try {

			Map<String, String> clientData = clientService.getDataForPartner(partnerCode);
			
			if (clientData.isEmpty()) {
				return ResponseHandler.generateResponse(null, "No Content Found",0);

			}

			return ResponseHandler.generateResponse(clientData, "Success", 0);

		} catch (Exception ex) {
			
			return ResponseHandler.generateResponse("", "INTERNAL_SERVER_ERROR",500);

		}

	}

}
