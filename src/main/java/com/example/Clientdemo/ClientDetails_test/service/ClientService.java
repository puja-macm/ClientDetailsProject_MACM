package com.example.Clientdemo.ClientDetails_test.service;

import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Clientdemo.ClientDetails_test.ClientDetailsTestApplication;
import com.example.Clientdemo.ClientDetails_test.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;

	public Map<String, String> getData(String id) throws SQLException {
		final Logger LOGGER=LoggerFactory.getLogger(ClientService.class);
		Map<String, String> data = clientRepository.getDataFromRedis(id);
		System.out.println("data is" + data);
		LOGGER.info("DATA from Redis::"+ data);
		if (data.isEmpty()) {
			data = clientRepository.getDataFromMySQL(id);
			LOGGER.info("DATA from MySQL Database::"+ data);
			clientRepository.storeDataInRedis(id, data);
			LOGGER.info("DATA from Redis after adding from mysqldb::"+ data);
		}

		return data;
	}

}
