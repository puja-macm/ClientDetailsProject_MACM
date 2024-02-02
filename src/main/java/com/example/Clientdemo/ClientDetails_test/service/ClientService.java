package com.example.Clientdemo.ClientDetails_test.service;

import java.sql.SQLException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Clientdemo.ClientDetails_test.AppConstant;
import com.example.Clientdemo.ClientDetails_test.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

	public Map<String, String> getData(String id) throws SQLException {
		Map<String, String> data = clientRepository.getDataFromRedis(id);
		Map<String, String> partnerData;
		System.out.println("data is" + data);
		LOGGER.info("DATA from Redis::" + data);
		//if data is not available in Redis
		if (data.isEmpty() || data.containsKey("partnerCode")) {
			data = clientRepository.getDataFromMySQL(id);
			//get partner data from getDataFromPartnerProcedure method
			//partnerData = clientRepository.getDataFromPartnerProcedure(id);
			//LOGGER.info("DATA from partner::" + partnerData);
			//add partner data in data map
			/*
			 * for (Map.Entry<String, String> entry : partnerData.entrySet()) {
			 * 
			 * data.put(AppConstant.IP_PARTNER_CODE, partnerData.get("partnerCode"));
			 * data.put(AppConstant.IP_EMAIL, partnerData.get("partnerEmail"));
			 * data.put(AppConstant.IP_MOBILE_NUMBER,
			 * partnerData.get("partnerMobileNumber")); data.put(AppConstant.IP_NAME,
			 * partnerData.get("partnerName")); }
			 */
			LOGGER.info("DATA from MySQL Database::" + data);
			clientRepository.storeDataInRedis(id, data);
			LOGGER.info("DATA from Redis after adding from mysqldb::" + data);
		}
		//after storing data in Redis calling this method to send data to API
		data = clientRepository.getDataFromRedis(id);
		return data;
	}

	public Map<String, String> getDataForPartner(String id) {

		Map<String, String> partnerData = clientRepository.getDataFromRedisForPartner(id);
		LOGGER.info("DATA from Redis::" + partnerData);
		if (partnerData.isEmpty()) {
			partnerData = clientRepository.getDataFromPartnerProcedure(id);
			LOGGER.info("DATA from Procedure::" + partnerData);
			clientRepository.storeDataInRedisForPartner(id, partnerData);

		}

		return partnerData;
	}

}
