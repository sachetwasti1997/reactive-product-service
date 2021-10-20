package com.sachet.productservice.util;

import com.sachet.productservice.dto.ProductDto;
import com.sachet.productservice.entity.Product;

public class EntityDtoUtil {

    public static ProductDto toDto(Product product){
        return new ProductDto(product.getId(), product.getDescription(), product.getPrice());
    }

    public static Product toProduct(ProductDto productDto){
        return new Product(productDto.getId(), productDto.getDescription(), productDto.getPrice());
    }

}
