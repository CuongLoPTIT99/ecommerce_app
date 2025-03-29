//package com.microservice.apigateway.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
//import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import java.time.Instant;
//
//public class TokenRefreshFilter implements WebFilter {
//
//    private final ReactiveOAuth2AuthorizedClientManager authorizedClientManager;
//
//    public TokenRefreshFilter(ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
//        this.authorizedClientManager = authorizedClientManager;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        return exchange.getPrincipal()
//                .cast(OAuth2AuthenticationToken.class)
//                .flatMap(authentication -> {
//                    String clientRegistrationId = authentication.getAuthorizedClientRegistrationId();
//                    String principalName = authentication.getName();
//
//                    // Lấy authorized client
//                    return authorizedClientManager.authorize(
//                                    OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId)
//                                            .principal(authentication)
//                                            .build())
//                            .flatMap(authorizedClient -> {
//                                // Kiểm tra xem access token có hết hạn không
//                                Instant accessTokenExpiresAt = authorizedClient.getAccessToken().getExpiresAt();
//                                if (accessTokenExpiresAt != null && accessTokenExpiresAt.isBefore(Instant.now())) {
//                                    // Nếu có refresh token
//                                    if (authorizedClient.getRefreshToken() != null) {
//                                        // authorizedClientManager sẽ tự động refresh token vì đã được cấu hình
//                                        // Sau khi refreshed, tiếp tục chuỗi filter
//                                        return chain.filter(exchange);
//                                    } else {
//                                        // Chuyển hướng đến trang đăng nhập Keycloak nếu không có refresh token
//                                        return redirectToLogin(exchange);
//                                    }
//                                }
//
//                                // Token vẫn còn hiệu lực
//                                return chain.filter(exchange);
//                            })
//                            .onErrorResume(e -> {
//                                // Xử lý lỗi (thường là lỗi xác thực)
//                                return redirectToLogin(exchange);
//                            });
//                })
//                .switchIfEmpty(redirectToLogin(exchange));
//    }
//
//    private Mono<Void> redirectToLogin(ServerWebExchange exchange) {
//        exchange.getResponse().setStatusCode(HttpStatus.FOUND);
//        exchange.getResponse().getHeaders().add("Location", "/oauth2/authorization/keycloak");
//        return exchange.getResponse().setComplete();
//    }
//}
