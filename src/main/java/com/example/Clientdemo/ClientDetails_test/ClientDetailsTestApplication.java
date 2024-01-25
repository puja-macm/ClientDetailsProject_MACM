package com.example.Clientdemo.ClientDetails_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;


@SpringBootApplication(exclude ={DataSourceAutoConfiguration.class })
public class ClientDetailsTestApplication {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(ClientDetailsTestApplication.class);

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(ClientDetailsTestApplication.class, args);
		
	
			}
	}


