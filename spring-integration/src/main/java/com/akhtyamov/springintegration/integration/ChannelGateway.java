package com.akhtyamov.springintegration.integration;

import com.akhtyamov.springintegration.domain.Product;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface ChannelGateway {
    @Gateway(requestChannel = "channel")
    void process(Product product);
}
