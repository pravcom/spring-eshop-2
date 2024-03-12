package com.akhtyamov.springintegration.domain;

import java.util.UUID;

public class Product {
    private String id;
    private String title;
    private Double price;

    public Product() {
    }

    public Product(String title, Double price) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
