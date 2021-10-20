package com.sachet.productservice.dto;

import javax.validation.constraints.NotNull;
import java.util.function.Supplier;

public class ProductDto {

    private String id;
    @NotNull
    private String description;
    @NotNull
    private Double price;

    public ProductDto(String description, Double price) {
        this.description = description;
        this.price = price;
    }

    public ProductDto(String id, String description, Double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public ProductDto() {
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

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
