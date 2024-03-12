package com.akhtyamov.springeshop.service;

import com.akhtyamov.springeshop.config.OrderIntegrationConfig;
import com.akhtyamov.springeshop.dao.OrderRepository;
import com.akhtyamov.springeshop.domain.Order;
import com.akhtyamov.springeshop.dto.OrderIntegrationDTO;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    public final OrderRepository orderRepository;
    public final OrderIntegrationConfig integrationConfig;

    public OrderServiceImpl(OrderRepository orderRepository, OrderIntegrationConfig integrationConfig) {
        this.orderRepository = orderRepository;
        this.integrationConfig = integrationConfig;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        Order saveOrder = orderRepository.save(order);
        sendIntegrationNotify(saveOrder);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void sendIntegrationNotify(Order order){
        OrderIntegrationDTO dto = new OrderIntegrationDTO();

        dto.setUsername(order.getUser().getName());
        dto.setAddress(order.getAddress());
        dto.setOrderId(order.getId());

        List<OrderIntegrationDTO.OrderDetailsDTO> details = order.getDetails().stream()
                .map(OrderIntegrationDTO.OrderDetailsDTO::new).collect(Collectors.toList());

        dto.setDetails(details);

        Message<OrderIntegrationDTO> message = MessageBuilder.withPayload(dto)
                .setHeader("Content type", "application/json")
                .build();

        integrationConfig.getOrderChannel().send(message);
    }

}
