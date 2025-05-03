package com.ecommerceapp.commonmodule.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
public class WebClientAPIService implements APIService {
    private static final Logger logger = LoggerFactory.getLogger(WebClientAPIService.class);
    private final WebClient webClient;

    public WebClientAPIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public <T> T get(String url, Class<T> responseType) throws Exception {
        try {
            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block(); // Synchronous
        } catch (WebClientResponseException ex) {
            // log error or rethrow custom exception
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public <T> T post(String url, Map<String, Object> requestBody, Class<T> responseType) throws Exception {
        try {
            return webClient.post()
                    .uri(url)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public <T> T put(String url, Map<String, Object> requestBody, Class<T> responseType) throws Exception {
        try {
            return webClient.post()
                    .uri(url)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void delete(String url) throws Exception {
        try {
            webClient.delete()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientResponseException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }
}
