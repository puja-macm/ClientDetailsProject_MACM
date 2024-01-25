package com.example.Clientdemo.ClientDetails_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ClientDetailsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientDetailsTestApplication.class, args);


    }
}


