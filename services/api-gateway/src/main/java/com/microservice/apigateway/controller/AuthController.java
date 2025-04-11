package com.microservice.apigateway.controller;

import com.microservice.apigateway.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OAuth2Service oAuth2Service;

    @GetMapping("/login")
    public Mono<Void> login(ServerWebExchange exchange) {
        return oAuth2Service.redirect2Login(exchange);
    }

    @GetMapping("/callback")
    public Mono<Void> callback(@RequestParam MultiValueMap<String, String> params, ServerWebExchange exchange) {
        return oAuth2Service.exchangeCode4Token(exchange, params.getFirst("code"));
    }

    @PostMapping("/logout")
    public Mono<Void> logout(ServerWebExchange exchange) {
        return oAuth2Service.logout(exchange);
    }

    @GetMapping("/userinfo")
    public Mono<String> getUserInfo(ServerWebExchange exchange) {
        return oAuth2Service.getUserInfo(exchange);
    }
}


