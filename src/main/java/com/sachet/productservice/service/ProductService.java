package com.sachet.productservice.service;

import com.sachet.productservice.dto.ProductDto;
import com.sachet.productservice.entity.Product;
import com.sachet.productservice.repository.ProductRepository;
import com.sachet.productservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<ProductDto> findAll(){
        return productRepository
                .findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> findById(String id){
        return productRepository
                .findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> saveProductOrUpdate(Mono<ProductDto> productDtoMono){
        return productDtoMono
                .map(EntityDtoUtil::toProduct)
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> updateProduct(Mono<ProductDto> product, String id){
        return productRepository
                .findById(id)
                .flatMap(product1 -> {
                    return product
                            .flatMap(productDto -> {
                                product1.setDescription(productDto.getDescription());
                                product1.setPrice(productDto.getPrice());
                                return productRepository.save(product1);
                            });
                })
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ResponseEntity<Void>> deleteProductById(String id){
        return productRepository
                .findById(id)
                .flatMap(product -> productRepository.delete(product).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
    }

    public Mono<ResponseEntity<Void>> deleteProduct(Mono<ProductDto> productDtoMono){
        return productRepository
                .findById(productDtoMono.map(ProductDto::getId))
                .flatMap(product -> productRepository.delete(product).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))));
    }

    public Mono<Void> deleteAll(){
        return productRepository
                .deleteAll();
    }
}
