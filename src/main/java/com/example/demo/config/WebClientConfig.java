package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("nbu.currency.url")
    String nbuCur;

    @Bean
    public WebClient nbuCurrClient(WebClient.Builder builder) {
        return builder
                .baseUrl(nbuCur)
                .build();
    }

    @Bean
    public WebClient paymentClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.payments.com")
                .build();
    }
}
