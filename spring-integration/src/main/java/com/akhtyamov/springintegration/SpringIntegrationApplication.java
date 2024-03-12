package com.akhtyamov.springintegration;

import com.akhtyamov.springintegration.domain.Product;
import com.akhtyamov.springintegration.integration.ChannelGateway;
import orders.SpringOrdersClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@IntegrationComponentScan
public class SpringIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringIntegrationApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8090"));
        ConfigurableApplicationContext context = app.run(args);

//        ConfigurableApplicationContext context = SpringApplication.run(SpringIntegrationApplication.class, args);

//		Message<String> message = MessageBuilder
//				.withPayload("Its a body")
//				.setHeader("StringHeader", "Value")
//				.setHeader("IntHeader", 1)
//				.setHeader("ListOfStrings", Arrays.asList("String1", "String2"))
//				.build();
//
//		DirectChannel channel = context.getBean(DirectChannel.class);
//		channel.send(message);
//
//		ChannelGateway channelGateway = context.getBean(ChannelGateway.class);
//		channelGateway.process(new Product("Milk", 34.34));
//		channelGateway.process(new Product("Chocolate" ,111.22));
//
//		DirectChannel invokeCallGetProducts = context.getBean("invokeCallGetProducts", DirectChannel.class);
//		invokeCallGetProducts.send(MessageBuilder.withPayload("").build()); //empty message for init
//
//		PollableChannel productsChannel = context.getBean("get_products_channel", PollableChannel.class);
//		Message<?> receive = productsChannel.receive();
//		System.out.println(receive);
//		System.out.println(receive.getPayload());
    }

}

