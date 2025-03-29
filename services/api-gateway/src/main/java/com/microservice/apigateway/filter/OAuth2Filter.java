//package com.microservice.apigateway.filter;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpCookie;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseCookie;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.client.*;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.security.oauth2.core.OAuth2RefreshToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import java.time.Duration;
//import java.util.Objects;
//
//@RequiredArgsConstructor
//public class OAuth2Filter implements WebFilter {
//
//    private final OAuth2AuthorizedClientManager authorizedClientManager;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        HttpCookie refreshTokenCookie = exchange.getRequest().getCookies().getFirst("refresh_token");
//
////        if (!Objects.isNull(refreshTokenCookie)) {
////            String refreshToken = refreshTokenCookie.getValue();
////            OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) exchange.getPrincipal().block();
////            OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
////                    .withClientRegistrationId(authenticationToken.getAuthorizedClientRegistrationId())
////                    .principal(authenticationToken)
////                    .build();
////            OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
////            // Store the new access token in an HTTP-only cookie
////            ResponseCookie accessTokenCookie = ResponseCookie.from("access_token", newAccessToken)
////                    .httpOnly(true)
////                    .secure(true)
////                    .path("/")
////                    .maxAge(Duration.ofMinutes(30)) // Set expiration time
////                    .build();
////
////            exchange.getResponse().getHeaders().add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
////        }
//
//        return chain.filter(exchange);
//    }
//}
//
