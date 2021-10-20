package com.sachet.productservice.entity;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class Product {

    @Id
    private String id;
    @NotNull
    private String description;
    @NotNull
    private Double price;

    public Product(String description, Double price) {
        this.description = description;
        this.price = price;
    }

    public Product(String id, String description, Double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
