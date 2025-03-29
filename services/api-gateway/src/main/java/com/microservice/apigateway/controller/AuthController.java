package com.microservice.apigateway.controller;

import com.microservice.apigateway.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
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


//    @GetMapping("/access-token")
//    public Mono<ResponseEntity<?>> getToken(OAuth2AuthenticationToken authentication,
//                                                              ServerWebExchange exchange) {
//        return authorizedClientRepository.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication, exchange)
//                .map(client -> {
//                    String accessToken = client.getAccessToken().getTokenValue();
//
//                    // Store refresh token in HTTP-only cookie
//                    ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
//                            .httpOnly(true)
//                            .secure(false)
//                            .path("/auth")
//                            .maxAge(Duration.ofMinutes(30))
//                            .build();
//
//                    return ResponseEntity.ok()
//                            .header(HttpHeaders.SET_COOKIE, cookie.toString())
//                            .body("Token is set to HttpOnly Cookie");
//                });
//    }

    @RequestMapping(path = "/status", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    public Mono<?> checkAuthStatus() {
//        if (Objects.isNull(oidcUser)) {
//            return ResponseEntity.status(401).body(Map.of("authenticated", false));
//        }
//
//        String accessToken = authToken.getPrincipal().getAttributes().get("access_token").toString();
//        String refreshToken = authToken.getPrincipal().getAttributes().get("refresh_token").toString();
//
//        // Store Access Token in HTTP-Only Cookie
//        ResponseCookie accessTokenCookie = ResponseCookie.from("access_token", accessToken)
//                .httpOnly(true)
//                .secure(false)
//                .path("/auth")
//                .maxAge(Duration.ofMinutes(30))  // Expiry same as token
//                .build();
//
//        // Store Refresh Token in HTTP-Only Cookie
//        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", refreshToken)
//                .httpOnly(true)
//                .secure(false)
//                .path("/auth")
//                .maxAge(Duration.ofDays(7))  // Refresh token lasts longer
//                .build();

//        return ResponseEntity.ok()
////                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
////                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
//                .body(Map.of("authenticated", true));
        return Mono.just("alo");
    }
}


