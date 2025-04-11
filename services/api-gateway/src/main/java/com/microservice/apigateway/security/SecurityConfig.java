package com.microservice.apigateway.security;

import com.microservice.apigateway.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.*;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2Service oAuth2Service;
    private final ReactiveJwtDecoder jwtDecoder;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/**", "/auth/**").permitAll()
                        .pathMatchers("/auth/customer/create").access((authentication, context) -> authorizationHandler(context.getExchange()))
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .bearerTokenConverter(bearerTokenConverter())
                        .authenticationManagerResolver(resolver -> Mono.just(authenticationManager()))
                        .authenticationFailureHandler(authenticationFailureHandler())
                        .accessDeniedHandler(accessDeniedHandler())
                )
        ;
        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    public ServerWebExchangeMatcher noRequiredAuthMatcher() {
        return exchange -> {
            OrServerWebExchangeMatcher orMatcher = new OrServerWebExchangeMatcher(
                    List.of(
                            new PathPatternParserServerWebExchangeMatcher("/auth/login"),
                            new PathPatternParserServerWebExchangeMatcher("/auth/callback"),
                            new PathPatternParserServerWebExchangeMatcher("/auth/logout")
                    )
            );
            return orMatcher.matches(exchange);
        };
    }

    public ServerAuthenticationConverter bearerTokenConverter() {
        return exchange ->
            noRequiredAuthMatcher().matches(exchange)
                    .flatMap(matchResult -> {
                        if (matchResult.isMatch()) {
                            // Matched: no auth required — skip auth
                            return Mono.empty();
                        } else {
                            // Not matched: check authentication by bearer token
                            HttpCookie accessToken = exchange.getRequest().getCookies().getFirst("access_token");
                            if (Objects.nonNull(accessToken)) return Mono.just(new BearerTokenAuthenticationToken(accessToken.getValue()));
                            else return Mono.error(new InvalidBearerTokenException("Access token not found"));
                        }
                    });
    }

    public ReactiveAuthenticationManager authenticationManager() {
        return authentication -> {
            String accessToken = Optional.ofNullable(authentication.getCredentials())
                    .map(Object::toString).orElse(null);
            return oAuth2Service.isBlacklistedToken(accessToken)
                    .flatMap(exist -> {
                        if (exist) {
                            authentication.setAuthenticated(false);
                            return Mono.just(authentication);
                        } else {
                            JwtReactiveAuthenticationManager jwtAuthenticationManager = new JwtReactiveAuthenticationManager(jwtDecoder);
                            return jwtAuthenticationManager.authenticate(authentication);
                        }
                    });
        };
    }

    public Mono<AuthorizationDecision> authorizationHandler(ServerWebExchange exchange) {
        String accessToken = Optional.ofNullable(exchange.getRequest().getCookies().getFirst("access_token"))
                .map(HttpCookie::getValue).orElse(null);
        if (Objects.isNull(accessToken)) return Mono.just(new AuthorizationDecision(false));
        String path = exchange.getRequest().getPath().value();
        String resource = "", scope = "";
        if (path.startsWith("/auth/")) {
            String[] pathParts = path.replaceFirst("/auth/", "").split("/");
            if (pathParts.length >= 2) {
                resource = pathParts[0];
                scope = pathParts[1];
            }
        }
        return oAuth2Service.hasPermission(accessToken, resource + "#" + scope)
                .filter(Boolean::booleanValue)
                .map(granted -> new AuthorizationDecision(true))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    public ServerAuthenticationFailureHandler authenticationFailureHandler() {
        return (webFilterExchange, exception) -> {
            HttpCookie refreshTokenCookie = webFilterExchange.getExchange().getRequest().getCookies().getFirst("refresh_token");
            if (Objects.nonNull(refreshTokenCookie)) {
                String refreshToken = refreshTokenCookie.getValue();
                return oAuth2Service.refreshAccessToken(refreshToken, webFilterExchange.getExchange());
            } else {
                webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            }
            return webFilterExchange.getExchange().getResponse().setComplete();
        };
    }

    public ServerAccessDeniedHandler accessDeniedHandler() {
        return (webFilterExchange, exception) -> {
            webFilterExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return webFilterExchange.getResponse().setComplete();
        };
    }
}
