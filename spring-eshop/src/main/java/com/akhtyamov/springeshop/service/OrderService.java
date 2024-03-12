package com.akhtyamov.springeshop.service;

import com.akhtyamov.springeshop.domain.Order;

public interface OrderService {
    void saveOrder(Order order);

    Order getOrder(Long id);
}
