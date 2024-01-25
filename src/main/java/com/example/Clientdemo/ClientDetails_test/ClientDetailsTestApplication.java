package com.example.Clientdemo.ClientDetails_test;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ClientDetailsTestApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(ClientDetailsTestApplication.class, args);

	}
}
