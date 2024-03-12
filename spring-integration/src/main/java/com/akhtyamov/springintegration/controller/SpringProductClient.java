package com.akhtyamov.springintegration.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;

@SpringBootApplication
public class SpringProductClient {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringProductClient.class);
        app.setDefaultProperties(Collections.singletonMap("server.port","8083"));
        ConfigurableApplicationContext context = app.run(args);
    }

}
