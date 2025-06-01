package com.ecommerceapp.commonmodule.network.api;

import com.ecommerceapp.commonmodule.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WebClientAPIService implements APIService {
    private final WebClient webClient;

    public WebClientAPIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public <T> T get(String url, ParameterizedTypeReference<T> responseType) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType)
                .doOnError(ex -> log.error("Error during GET request: {}", ex.getMessage()))
                .block();
    }

    @Override
    public <T> T post(String url, Map<String, Object> requestBody, ParameterizedTypeReference<T> responseType) {
        return webClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType)
                .doOnError(ex -> log.error("Error during POST request: {}", ex.getMessage()))
                .block();
    }

    @Override
    public <T> T put(String url, Map<String, Object> requestBody, ParameterizedTypeReference<T> responseType) {
        return webClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType)
                .doOnError(ex -> log.error("Error during PUT request: {}", ex.getMessage()))
                .block();
    }

    @Override
    public void delete(String url) {
        webClient.delete()
                .uri(url)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnError(ex -> log.error("Error during DELETE request: {}", ex.getMessage()))
                .block();
    }
}
