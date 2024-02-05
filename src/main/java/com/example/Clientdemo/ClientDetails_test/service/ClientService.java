package com.example.Clientdemo.ClientDetails_test.service;

import java.sql.SQLException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Clientdemo.ClientDetails_test.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

	public Map<String, String> getData(String id) throws SQLException {
		Map<String, String> data = clientRepository.getDataFromRedis(id);
		LOGGER.info("DATA from Redis::" + data);
		//if data is not available in Redis
		if (data.isEmpty() || data.containsKey("partnerCode")) {
			data = clientRepository.getDataFromMySQL(id);
			LOGGER.info("DATA from MySQL Database::" + data);
			clientRepository.storeDataInRedis(id, data);
			LOGGER.info("DATA from Redis after adding from mysqldb::" + data);
		}
		//after storing data in Redis calling this method to send data to API
		data = clientRepository.getDataFromRedis(id);
		return data;
	}
//method to get partner details by passing partnercode
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
