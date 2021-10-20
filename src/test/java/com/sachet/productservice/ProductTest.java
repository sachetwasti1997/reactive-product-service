package com.sachet.productservice;

import com.sachet.productservice.dto.ProductDto;
import com.sachet.productservice.dto.Response;
import com.sachet.productservice.entity.Product;
import com.sachet.productservice.exception.HttpStatusException;
import com.sachet.productservice.service.ProductService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class ProductTest extends ProductServiceApplicationTests{

    @Autowired
    ProductService productService;

    @Autowired
    WebTestClient webClient;

    @AfterEach
    public void deleteAll(){
        productService
                .deleteAll()
                .subscribe();
    }

    private ProductDto createProduct(ProductDto product){
        return productService
                .saveProductOrUpdate(Mono.just(product))
                .block();
    }

    private Mono<ProductDto> createProductMono(ProductDto product){
        return productService
                .saveProductOrUpdate(Mono.just(product));
    }

    private static Mono<Product> apply(ClientResponse result) {
            return result.bodyToMono(Product.class);
    }

    @Test
    public void productCreateTest(){
        ProductDto productDtoMono = new ProductDto("Renault Duster", 2200000.00);

        WebTestClient.ResponseSpec productDto = webClient
                .post()
                .uri("/product/save")
                .bodyValue(productDtoMono)
                .exchange();

        productDto
                .expectStatus()
                .isOk();

        Object ob = productDto
                .expectBody(ProductDto.class)
                .returnResult()
                .getResponseBody();

        System.out.println(ob);

    }

    @Test
    public void productCreateDescriptionNullReceiveError(){
        ProductDto productDto = new ProductDto();

        WebTestClient.ResponseSpec responseSpec = webClient
                .post()
                .uri("/product/save")
                .bodyValue(productDto)
                .exchange();

        responseSpec
                .expectStatus()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void deleteProductByIdProductExistReceiveOk(){
        ProductDto productDto = createProductMono(new ProductDto("Pen", 12.00)).block();

        WebTestClient.ResponseSpec responseSpec = webClient
                .delete()
                .uri("/product/{id}", productDto.getId())
                .exchange();

        responseSpec
                .expectStatus()
                .isOk();
    }

    @Test
    public void deleteProductProductNotExistReceiveError(){
        WebTestClient.ResponseSpec responseSpec = webClient
                .delete()
                .uri("/{id}", 123)
                .exchange();

        responseSpec
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void deleteProductReceiveOk() throws JSONException {
        Mono<ProductDto> productDto = createProductMono(new ProductDto("Pen", 12.00));

        WebTestClient.ResponseSpec responseSpec = webClient
                .method(HttpMethod.DELETE)
                .uri("/product/delete")
                .body(productDto, ProductDto.class)
                .exchange();

        responseSpec
                .expectStatus()
                .isOk();
    }

    @Test
    public void deleteProductProductDoesNotExistReceiveNotFound(){
        WebTestClient.ResponseSpec responseSpec = webClient
                .method(HttpMethod.DELETE)
                .uri("/product/delete")
                .body(Mono.just(new ProductDto("123","This is sample Product", 12.00)), ProductDto.class)
                .exchange();

        responseSpec
                .expectStatus()
                .isNotFound();
    }

    @Test
    public void getAllProducts(){
        WebTestClient.ResponseSpec responseSpec = webClient
                .get()
                .uri("/product")
                .exchange();

        responseSpec
                .expectStatus()
                .isOk();
    }

    @Test
    public void getProductById(){
        ProductDto productDto = createProductMono(new ProductDto("Cappuccino", 130.00)).block();

        WebTestClient.ResponseSpec responseSpec = webClient
                .get()
                .uri("/product/{id}", productDto.getId())
                .exchange();

        responseSpec
                .expectStatus()
                .isOk();

         responseSpec
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    public void findProductByIdProductDoesNotExistReceiveNotFound(){
        WebTestClient.ResponseSpec responseSpec = webClient
                .get()
                .uri("/product/{id}", 123)
                .exchange();

        responseSpec
                .expectStatus()
                .isNotFound();

        responseSpec
                .expectBody(HttpStatusException.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    public void updateProductProductExistReceiveOk(){
        ProductDto productDto = createProductMono(new ProductDto("Fountain Pen", 12.00)).block();
        productDto.setDescription("Fountain Pen 5 Star");
        WebTestClient.ResponseSpec responseSpec = webClient
                .put()
                .uri("/product/update/{id}", productDto.getId())
                .bodyValue(productDto)
                .exchange();

        responseSpec
                .expectStatus()
                .isOk();
    }

    @Test
    public void updateProductProductNotExistReceiveNotFound(){
        WebTestClient.ResponseSpec responseSpec = webClient
                .put()
                .uri("/product/update/{id}", 123)
                .bodyValue(new ProductDto("Test Product", 12.00))
                .exchange();

        responseSpec
                .expectStatus()
                .isNotFound();
    }

}














