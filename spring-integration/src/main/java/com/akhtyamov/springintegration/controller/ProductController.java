package com.akhtyamov.springintegration.controller;

import com.akhtyamov.springintegration.domain.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Value("${server.port:8080}")
    private int port;
    private static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product("Milk", 55.55));
        products.add(new Product("Beer", 66.66));
        products.add(new Product("Chocolate", 166.66));
        products.add(new Product("Bread", 30.00));
    }

    @GetMapping
    public List<Product> getProducts() {
        System.out.println("Server port -> " + port);
        return products;
    }
}
