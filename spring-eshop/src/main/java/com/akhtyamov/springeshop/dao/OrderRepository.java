package com.akhtyamov.springeshop.dao;

import com.akhtyamov.springeshop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
