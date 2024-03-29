package com.akhtyamov.cloudrouter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@SpringBootApplication
@EnableZuulProxy
public class CloudRouterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudRouterApplication.class, args);
    }

}
