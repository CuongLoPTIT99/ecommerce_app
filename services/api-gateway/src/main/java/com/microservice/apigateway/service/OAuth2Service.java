package com.microservice.apigateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

@Service
public class OAuth2Service {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Service.class);
    private final WebClient webClient;
    private final ReactiveClientRegistrationRepository clientRegistrationRepository;
    private final Mono<ClientRegistration> clientRegistration;
    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private final ReactiveJwtDecoder jwtDecoder;

    public OAuth2Service(
            WebClient.Builder webClientBuilder,
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ReactiveRedisTemplate<String, String> redisTemplate,
            ReactiveJwtDecoder jwtDecoder
    ) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.clientRegistration = this.clientRegistrationRepository.findByRegistrationId("keycloak");
        this.redisTemplate = redisTemplate;
        this.jwtDecoder = jwtDecoder;
    }

    public Mono<Void> redirect2Login(ServerWebExchange exchange) {
        return clientRegistration.flatMap(clientRegistration -> {
            String authUrl = clientRegistration.getProviderDetails()
                    .getConfigurationMetadata()
                    .get("authorization_endpoint").toString() +
                    "?client_id=" + clientRegistration.getClientId() +
                    "&response_type=code&scope=" + clientRegistration.getScopes().toString().replaceAll("[\\[\\] ]", "") +
                    "&redirect_uri=" + clientRegistration.getRedirectUri() +
                    "&state=" + generateRandomString();
//                    + "&code_challenge_method=S256&code_challenge=" + codeChallenge;

            // Store the code verifier in an HTTP-only cookie (for later use)
//            ResponseCookie cookie = ResponseCookie.from("code_verifier", codeVerifier)
//                    .httpOnly(true)
//                    .secure(false)
//                    .path("/")
//                    .sameSite("Strict")
//                    .build();
//            exchange.getResponse().addCookie(cookie);

            exchange.getResponse().setStatusCode(HttpStatus.FOUND);
            exchange.getResponse().getHeaders().setLocation(URI.create(authUrl));
            return exchange.getResponse().setComplete();
        });
    }

    public Mono<Void> exchangeCode4Token(ServerWebExchange exchange, String code) {
        return clientRegistration.flatMap(clientRegistration -> {
            // Retrieve the code verifier from the cookie
//            HttpCookie codeVerifier = exchange.getRequest().getCookies().getFirst("code_verifier");
            return this.webClient.post()
                    .uri(clientRegistration.getProviderDetails()
                            .getConfigurationMetadata()
                            .get("token_endpoint").toString())  // External API endpoint
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData("grant_type", clientRegistration.getAuthorizationGrantType().getValue())
                                    .with("client_id", clientRegistration.getClientId())
                                    .with("client_secret", clientRegistration.getClientSecret())
                                    .with("code", code)
                                    .with("redirect_uri", clientRegistration.getRedirectUri())
//                            .with("code_verifier", codeVerifier != null ? codeVerifier.getValue() : "")
                    )
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .flatMap(response -> {
                        Map<String, Object> responseBody = response;
                        String accessToken = responseBody.get("access_token").toString();
                        String refreshToken = responseBody.get("refresh_token").toString();

                        updateTokenCookies(exchange.getResponse(), accessToken, refreshToken);

                        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                        exchange.getResponse().getHeaders().setLocation(URI.create("http://localhost:4200/home"));
                        return exchange.getResponse().setComplete();
                    });
        });
    }

    public Mono<Void> refreshAccessToken(String refreshToken, ServerWebExchange exchange) {
        return clientRegistration.flatMap(clientRegistration ->
                webClient.post()
                        .uri(clientRegistration.getProviderDetails()
                                .getConfigurationMetadata()
                                .get("token_endpoint").toString())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .body(BodyInserters.fromFormData("grant_type", "refresh_token")
                                .with("client_id", clientRegistration.getClientId())
                                .with("client_secret", clientRegistration.getClientSecret())
                                .with("refresh_token", refreshToken))
                        .retrieve()
                        .bodyToMono(Map.class)
                        .flatMap(response -> {
                            String newAccessToken = (String) response.get("access_token");
                            String newRefreshToken = (String) response.get("refresh_token");

                            // Update cookies with new tokens
                            updateTokenCookies(exchange.getResponse(), newAccessToken, newRefreshToken);

                            exchange.getResponse().setStatusCode(HttpStatus.RESET_CONTENT);
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
            return clientRegistration.flatMap(clientRegistration ->
                            webClient.post()
                                    .uri(clientRegistration.getProviderDetails()
                                            .getConfigurationMetadata()
                                            .get("end_session_endpoint").toString())
                                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                    .body(BodyInserters.fromFormData("client_id", clientRegistration.getClientId())
                                                    .with("client_secret", clientRegistration.getClientSecret())
                                                    .with("refresh_token", refreshToken)
//                                                    .with("redirect_uri", "http://localhost:8080/auth/login")
                                    )
                                    .retrieve()
                                    .onStatus(HttpStatusCode::is2xxSuccessful, response -> {
                                        // Save access token into blacklist
                                        if (Objects.nonNull(exchange.getRequest().getCookies().getFirst("access_token"))) {
                                            String accessToken = exchange.getRequest().getCookies().getFirst("access_token").getValue();
                                            jwtDecoder.decode(accessToken).doOnSuccess(
                                                    jwt -> {
                                                        if (Objects.nonNull(jwt.getExpiresAt())) {
                                                            try {
                                                                String hashedToken = hashToken(accessToken);
                                                                redisTemplate.opsForValue().set("token:blacklist:" + hashedToken, "1", Duration.between(Instant.now(), jwt.getExpiresAt())).subscribe();
                                                            } catch (Exception e) {
                                                                Mono.error(new RuntimeException(e));
                                                            }
                                                        }
                                                    }
                                            ).subscribe();
                                        }
                                        return Mono.empty();
                                    })
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
            return exchange.getResponse().setComplete();
        }
    }

    public Mono<String> getUserInfo(ServerWebExchange exchange) {
        HttpCookie accessTokenCookie = exchange.getRequest().getCookies().getFirst("access_token");
        if (Objects.isNull(accessTokenCookie)) return Mono.empty();
        return clientRegistration.flatMap(clientRegistration ->
                webClient.get()
                        .uri(clientRegistration.getProviderDetails()
                                .getConfigurationMetadata()
                                .get("userinfo_endpoint").toString())
                        .header("Authorization", "Bearer " + accessTokenCookie.getValue())
                        .retrieve()
                        .bodyToMono(String.class)
        );
    }

    public Mono<Boolean> hasPermission(String accessToken, String permission) {
        return clientRegistration.flatMap(clientRegistration -> {
            try {
                // Check permission in Redis first
                String hashedToken = hashToken(accessToken);
                return redisTemplate.opsForValue().get("token:permission:" + hashedToken + ":" + permission)
                        .flatMap(value -> Mono.just(Boolean.valueOf(value)))
                        .switchIfEmpty(Mono.defer(() ->
                            // If permission not found in Redis, check with Keycloak
                            webClient.post()
                                    .uri(clientRegistration.getProviderDetails()
                                            .getConfigurationMetadata()
                                            .get("token_endpoint").toString())
                                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                    .body(BodyInserters.fromFormData("grant_type", "urn:ietf:params:oauth:grant-type:uma-ticket")
                                            .with("audience", clientRegistration.getClientId())
                                            .with("permission", permission)
                                            .with("response_mode", "decision")
                                    )
                                    .retrieve()
                                    .bodyToMono(Map.class)
                                    .flatMap(body -> {
                                        // Cache the permission checked with Keycloak
                                        redisTemplate.opsForValue().set("token:permission:" + hashedToken + ":" + permission, Boolean.TRUE.toString(), Duration.ofMinutes(1)).subscribe();
                                        return Mono.just(Boolean.TRUE.equals(body.get("result")));
                                    })
                                    .onErrorResume(Exception.class, ex -> {
                                        // Cache the permission checked with Keycloak
                                        redisTemplate.opsForValue().set("token:permission:" + hashedToken + ":" + permission, Boolean.FALSE.toString(), Duration.ofMinutes(1)).subscribe();
                                        return Mono.just(false);
                                    })
                        ))
                        .onErrorResume(ex -> {
                            logger.error("Error checking permission in Redis: {}", ex.getMessage());
                            return Mono.just(false);
                        });
            } catch (NoSuchAlgorithmException e) {
                return Mono.just(false);
            }
        });
    }

    public Mono<Boolean> isBlacklistedToken(String accessToken) {
        try {
            String hashedToken = hashToken(accessToken);
            return redisTemplate.opsForValue().get("token:blacklist:" + hashedToken)
                    .flatMap(value -> Mono.just(true))
                    .switchIfEmpty(Mono.just(false))
                    .onErrorResume(ex -> {
                        logger.error("Error checking token in Redis: {}", ex.getMessage());
                        return Mono.just(true);
                    });
        } catch (NoSuchAlgorithmException e) {
            return Mono.just(true);
        }
    }

    private String generateRandomString() {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(Instant.now().toString().getBytes(StandardCharsets.US_ASCII));
    }

//    private String generateCodeVerifier() {
//        byte[] randomBytes = new byte[32];
//        new SecureRandom().nextBytes(randomBytes);
//        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
//    }
//
//    private String generateCodeChallenge(String codeVerifier) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hashedBytes = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
//            return Base64.getUrlEncoder().withoutPadding().encodeToString(hashedBytes);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Error generating PKCE challenge", e);
//        }
//    }

    private String hashToken(String token) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(token.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b)); // convert byte to hex
        }
        return hexString.toString();
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
