package com.ecommerceapp.commonmodule.network.api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpRequest;

import java.net.http.HttpResponse;
import java.util.Map;

public interface APIService {
    <T> T get(String url, ParameterizedTypeReference<T> responseType);

    <T> T post(String url, Map<String, Object> requestBody, ParameterizedTypeReference<T> responseType);

    <T> T put(String url, Map<String, Object> requestBody, ParameterizedTypeReference<T> responseType);

    void delete(String url);
}
