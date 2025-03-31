package com.microservice.apigateway.service;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

@Service
public class OAuth2Service {
    private final WebClient webClient;
    private final ReactiveClientRegistrationRepository clientRegistrationRepository;

    public OAuth2Service(WebClient.Builder webClientBuilder, ReactiveClientRegistrationRepository clientRegistrationRepository) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    public Mono<Void> redirect2Login(ServerWebExchange exchange) {
        return clientRegistrationRepository.findByRegistrationId("keycloak")
                .flatMap(clientRegistration -> {

                    String codeVerifier = generateCodeVerifier();
                    String codeChallenge = generateCodeChallenge(codeVerifier);

                    String authUrl = clientRegistration.getProviderDetails()
                            .getConfigurationMetadata()
                            .get("authorization_endpoint").toString() +
                            "?client_id=" + clientRegistration.getClientId() +
                            "&response_type=code&scope=" + clientRegistration.getScopes().toString().replaceAll("[\\[\\] ]", "") +
                            "&redirect_uri=" + clientRegistration.getRedirectUri() +
                            "&code_challenge_method=S256&code_challenge=" + codeChallenge;

                    // Store the code verifier in an HTTP-only cookie (for later use)
                    ResponseCookie cookie = ResponseCookie.from("code_verifier", codeVerifier)
                            .httpOnly(true)
                            .secure(false)
                            .path("/")
                            .sameSite("Strict")
                            .build();
                    exchange.getResponse().addCookie(cookie);

                    exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                    exchange.getResponse().getHeaders().setLocation(URI.create(authUrl));
                    return exchange.getResponse().setComplete();
                });
    }

    public Mono<Void> exchangeCode4Token(ServerWebExchange exchange, String code) {
        return clientRegistrationRepository.findByRegistrationId("keycloak")
                .flatMap(clientRegistration -> {
                    // Retrieve the code verifier from the cookie
                    HttpCookie codeVerifier = exchange.getRequest().getCookies().getFirst("code_verifier");

                    return this.webClient.post()
                            .uri(clientRegistration.getProviderDetails()
                                    .getConfigurationMetadata()
                                    .get("token_endpoint").toString())  // External API endpoint
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .body(BodyInserters.fromFormData("grant_type", clientRegistration.getAuthorizationGrantType().getValue())
                                    .with("client_id", clientRegistration.getClientId())
                                    .with("code", code)
                                    .with("redirect_uri", clientRegistration.getRedirectUri())
                                    .with("code_verifier", codeVerifier != null ? codeVerifier.getValue() : ""))
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                            })
                            .flatMap(response -> {
                                Map<String, Object> responseBody = response;
                                String accessToken = responseBody.get("access_token").toString();
                                String refreshToken = responseBody.get("refresh_token").toString();

                                // Remove the code verifier in cookie
                                removeCookiesByName(exchange.getResponse(), "code_verifier");

                                updateTokenCookies(exchange.getResponse(), accessToken, refreshToken);

                                exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                                exchange.getResponse().getHeaders().setLocation(URI.create("http://localhost:4200/home"));
                                return exchange.getResponse().setComplete();
                            });
                });
    }

    public Mono<Void> refreshAccessToken(String refreshToken, ServerWebExchange exchange) {
        return clientRegistrationRepository.findByRegistrationId("keycloak")
                .flatMap(clientRegistration ->
                    webClient.post()
                            .uri(clientRegistration.getProviderDetails()
                                    .getConfigurationMetadata()
                                    .get("token_endpoint").toString())
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                            .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                                    .with("client_id", clientRegistration.getClientId())
                                    .with("refresh_token", refreshToken))
                            .retrieve()
                            .bodyToMono(Map.class)
                            .flatMap(response -> {
                                String newAccessToken = (String) response.get("access_token");
                                String newRefreshToken = (String) response.get("refresh_token");

                                // Update cookies with new tokens
                                updateTokenCookies(exchange.getResponse(), newAccessToken, newRefreshToken);
                                exchange.getResponse().setStatusCode(HttpStatus.OK);
                                return exchange.getResponse().setComplete();
                            })
                            .onErrorResume(Exception.class, ex -> {
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().setComplete();
                            })
                );
    }

    public Mono<Void> logout(ServerWebExchange exchange) {
        HttpCookie refreshTokenCookie = exchange.getRequest().getCookies().getFirst("refresh_token");
        if (Objects.nonNull(refreshTokenCookie)) {
            String refreshToken = refreshTokenCookie.getValue();
            return clientRegistrationRepository.findByRegistrationId("keycloak")
                    .flatMap(clientRegistration ->
                                    webClient.post()
                                            .uri(clientRegistration.getProviderDetails()
                                                    .getConfigurationMetadata()
                                                    .get("end_session_endpoint").toString())
                                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                            .body(BodyInserters.fromFormData("client_id", clientRegistration.getClientId())
                                                    .with("refresh_token", refreshToken)
//                                                    .with("redirect_uri", "http://localhost:8080/auth/login")
                                            )
                                            .retrieve()
                                            .toBodilessEntity()
                                            .flatMap(response -> {
                                                // Remove token in cookies
                                                removeCookiesByName(exchange.getResponse(), "access_token");
                                                removeCookiesByName(exchange.getResponse(), "refresh_token");

                                                exchange.getResponse().setStatusCode(HttpStatus.OK);
                                                return exchange.getResponse().setComplete();
                                            })
                    );
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                    .bufferFactory()
                    .wrap("Invalid credential: Refresh token not found".getBytes())));
            return exchange.getResponse().setComplete();
        }
    }

    private String generateCodeVerifier() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private String generateCodeChallenge(String codeVerifier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating PKCE challenge", e);
        }
    }

    private void updateTokenCookies(ServerHttpResponse response, String accessToken, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Strict")
                .build();
        response.addCookie(cookie);
        cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Strict")
                .build();
        response.addCookie(cookie);
    }

    private void removeCookiesByName(ServerHttpResponse response, String cookieName) {
        ResponseCookie expiredCookie = ResponseCookie.from(cookieName, "")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(0)
                .build();
        response.addCookie(expiredCookie);
    }
}
