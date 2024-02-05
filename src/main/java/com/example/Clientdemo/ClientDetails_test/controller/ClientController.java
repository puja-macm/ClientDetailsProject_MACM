package com.example.Clientdemo.ClientDetails_test.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	public Map<String, Object> getDataForClient(@RequestParam String clientCode)  throws IOException {
		try {

			Map<String, String> clientData = clientService.getData(clientCode);
			
			if (clientData.isEmpty()) {
				
				return ResponseHandler.generateResponse(null, "Client code not found", 1);

			}

			return ResponseHandler.generateResponse(clientData, "Success", 0);

		} catch (Exception ex) {
			
			return ResponseHandler.generateResponse("", "INTERNAL_SERVER_ERROR",500);

		}

	}
//This method is for getting partner details 
	@GetMapping("/getPartnerDetails")
	public Map<String, Object> getDataForPartner(@RequestParam String partnerCode) throws SQLException {
		try {

			Map<String, String> partnerData = clientService.getDataForPartner(partnerCode);
			
			if (partnerData.isEmpty()) {
				return ResponseHandler.generateResponse(null, "Client code not found", 1);

			}

			return ResponseHandler.generateResponse(partnerData, "Success", 0);

		} catch (Exception ex) {
			return ResponseHandler.generateResponse("", "INTERNAL_SERVER_ERROR",500);

		}

	}

}
