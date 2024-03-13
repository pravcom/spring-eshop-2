package com.akhtyamov.springeshop.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;

@Configuration
@ImportResource("classpath:/integration/http-orders-integration.xml")
public class OrderIntegrationConfig {
    private DirectChannel orderChannel;

    public OrderIntegrationConfig(@Qualifier("ordersChannel") DirectChannel orderChannel) {
        this.orderChannel = orderChannel;
    }

    public DirectChannel getOrderChannel() {
        return orderChannel;
    }
}
