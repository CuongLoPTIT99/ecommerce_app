package com.ecommerceapp.commonmodule.api;

import org.springframework.http.HttpRequest;

import java.net.http.HttpResponse;
import java.util.Map;

public interface APIService {
    <T> T get(String url, Class<T> responseType) throws Exception;

    <T> T post(String url, Map<String, Object> requestBody, Class<T> responseType) throws Exception;

    <T> T put(String url, Map<String, Object> requestBody, Class<T> responseType) throws Exception;

    void delete(String url) throws Exception;
}
