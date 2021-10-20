package com.sachet.productservice.controller;

import com.sachet.productservice.dto.ProductDto;
import com.sachet.productservice.exception.HttpStatusException;
import com.sachet.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private HttpStatusException buildError(){
        return new HttpStatusException("Not found", 404);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> findProductById(@PathVariable String id){
        Map<String, Integer>exception = new HashMap<>();
        return productService
                .findById(id)
                .map(productDto -> ResponseEntity
                        .ok()
                        .body(productDto)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public Flux<ProductDto> findAllProducts(){
        return productService
                .findAll();
    }

    @PostMapping("/save")
    public Mono<ProductDto> saveProduct(@Valid @RequestBody Mono<ProductDto> productDtoMono){
        return productService.saveProductOrUpdate(productDtoMono);
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable String id, @RequestBody Mono<ProductDto> productDtoMono){
        return productService
                .updateProduct(productDtoMono, id)
                .map(productDto -> ResponseEntity
                        .ok()
                        .body(productDto)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id){
        return productService
                .deleteProductById(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete")
    public Mono<ResponseEntity<Void>> delete(@RequestBody Mono<ProductDto> productDtoMono){
        return productService
                .deleteProduct(productDtoMono)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}















