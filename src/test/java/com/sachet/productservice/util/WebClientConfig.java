package com.sachet.productservice.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebTestClient webTestClient(){
        return WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:8080")
                .build();
    }

}
