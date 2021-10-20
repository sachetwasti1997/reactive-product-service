package com.sachet.productservice.dto;

import org.springframework.http.HttpStatus;

public class SuccessResponse extends Response{

    private ProductDto productDto;

    public SuccessResponse(String message, HttpStatus status, ProductDto productDto) {
        super(message, status);
        this.productDto = productDto;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    @Override
    public String toString() {
        return "SuccessResponse{" +
                "productDto=" + productDto +
                '}';
    }
}
