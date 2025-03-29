package com.microservice.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.*;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.server.authentication.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    @Bean
//    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
//        http
//                .authorizeExchange(authorize ->
//                        authorize.anyRequest().authenticated()
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .authorizationEndpoint(auth -> auth
//                                .authorizationRequestResolver(
//                                        new DefaultOAuth2AuthorizationRequestResolver(
//                                                clientRegistrationRepository(), "/oauth2/authorization")
//                                )
//                        )
//                );
//        return http.build();
//    }

//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(
//                keycloakClientRegistration()
//        );
//    }

//    private ClientRegistration keycloakClientRegistration() {
//        return ClientRegistration.withRegistrationId("keycloak")
//                .clientId("frontend-app")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("http://localhost:8080/login/oauth2/code/keycloak")
//                .scope("openid", "profile", "email")
//                .authorizationUri("http://localhost:9080/realms/ecommerce-app/protocol/openid-connect/auth")
//                .tokenUri("http://localhost:9080/realms/ecommerce-app/protocol/openid-connect/token")
//                .userInfoUri("http://localhost:9080/realms/ecommerce-app/protocol/openid-connect/userinfo")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .build();
//    }

//    private final ReactiveOAuth2AuthorizedClientManager authorizedClientManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
//                .cors(ServerHttpSecurity.CorsSpec::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(List.of("*"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers("/auth/login", "/oauth2/**", "/auth/callback").permitAll()
                        .anyExchange().authenticated()
                )
//                .oauth2Login(Customizer.withDefaults())
//                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
//                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter()
                                        )
                                )

                        ).bearerTokenConverter(bearerTokenConverter())
//                                .authenticationManagerResolver(authenticationManagerResolver -> authenticationManagerResolver
//                                        .authenticationManager(authentication -> {
//                                            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
//                                            return authorizedClientManager.authorize(
//                                                    OAuth2AuthorizeRequest.withAuthorizedClient(
//                                                            token.getAuthorizedClientRegistrationId(),
//                                                            token.getPrincipal().getName()
//                                                    )
//                                                            .principal(token)
//                                                            .attributes(token.getPrincipal().getAttributes())
//                                                            .build()
//                                            );
//                                        })
//                                ).

//                        .authenticationEntryPoint((exchange, exception) -> {
//                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                            Instant instant = Instant.now(Clock.systemUTC()).minus(Duration.of(60, ChronoUnit.SECONDS));
//                            Instant instant1 = Instant.now(Clock.systemUTC());
//                            return exchange.getResponse().setComplete();
//                        })
                                .accessDeniedHandler((webFilterExchange, exception) -> {
                                    System.out.println("Authentication failed");
                                    webFilterExchange.getResponse().setStatusCode(HttpStatus.FOUND);
                                    webFilterExchange.getResponse().getHeaders().add("Location", "/oauth2/authorization/keycloak");
                                    return webFilterExchange.getResponse().setComplete();
                                })
//                        .authenticationFailureHandler((webFilterExchange, exception) -> {
//                            System.out.println("Authentication failed");
//
//                            webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//
//                            // Store the access token in an HTTP-only cookie
//                            ResponseCookie cookie = ResponseCookie.from("access_token", "")
//                                    .path("/")
//                                    .httpOnly(true)
//                                    .secure(false)
//                                    .maxAge(0)
//                                    .build();
//                            webFilterExchange.getExchange().getResponse().addCookie(cookie);
//
//                            // Store the refresh token in an HTTP-only cookie
//                            cookie = ResponseCookie.from("refresh_token", "")
//                                    .path("/")
//                                    .httpOnly(true)
//                                    .secure(false)
//                                    .maxAge(0)
//                                    .build();
//                            webFilterExchange.getExchange().getResponse().addCookie(cookie);
//
//                            return webFilterExchange.getExchange().getResponse().setComplete();
//                        })
                )
//                .addFilterAfter(getOAuth2WebFilter(), SecurityWebFiltersOrder.AUTHENTICATION);
        ;

        return http.build();
    }

    public ServerAuthenticationConverter bearerTokenConverter() {
        return exchange -> {
            String pathReq = exchange.getRequest().getPath().value();
            if (pathReq.startsWith("/auth") && !pathReq.contains("status")) return Mono.empty();
            return Mono.justOrEmpty(exchange.getRequest().getCookies().getOrDefault("access_token", null))
                    .map(cookie -> new BearerTokenAuthenticationToken(!Objects.isNull(cookie) ? cookie.get(0).getValue() : ""));
        };
    }

//    public AuthenticationWebFilter getOAuth2WebFilter() {
//        AuthenticationWebFilter filter = new AuthenticationWebFilter(
//                new JwtReactiveAuthenticationManager(jwtDecoder())
//        );
//        filter.setServerAuthenticationConverter(
//                exchange -> Mono.justOrEmpty(exchange.getRequest().getCookies().getFirst("access_token"))
//                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access token not found")))
//                        .map(cookie -> new BearerTokenAuthenticationToken(cookie.getValue()))
//        );
//        filter.setAuthenticationFailureHandler((webFilterExchange, exception) -> {
//            System.out.println("Authentication failed");
//
//            webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//
//            // Store the access token in an HTTP-only cookie
//            ResponseCookie cookie = ResponseCookie.from("access_token", "")
//                    .path("/")
//                    .httpOnly(true)
//                    .secure(false)
//                    .maxAge(0)
//                    .build();
//            webFilterExchange.getExchange().getResponse().addCookie(cookie);
//
//            // Store the refresh token in an HTTP-only cookie
//            cookie = ResponseCookie.from("refresh_token", "")
//                    .path("/")
//                    .httpOnly(true)
//                    .secure(false)
//                    .maxAge(0)
//                    .build();
//            webFilterExchange.getExchange().getResponse().addCookie(cookie);
//
//            return webFilterExchange.getExchange().getResponse().setComplete();
//        });
//
//        return filter;
//    }

//    @Bean
//    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
//            ReactiveClientRegistrationRepository clientRegistrationRepository,
//            ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {
//
//        ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
//                ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
//                        .authorizationCode()
//                        .refreshToken()
//                        .build();
//
//        DefaultReactiveOAuth2AuthorizedClientManager authorizedClientManager =
//                new DefaultReactiveOAuth2AuthorizedClientManager(
//                        clientRegistrationRepository, authorizedClientRepository);
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//
//        return authorizedClientManager;
//    }

//    @Bean
//    public ServerAuthenticationSuccessHandler authenticationSuccessHandler() {
//        return (webFilterExchange, authentication) -> {
////            ResponseCookie accessTokenCookie = ResponseCookie.from(
////                    "access_token",
////                            ((DefaultOidcUser) authentication.getPrincipal()).getIdToken().getTokenValue()
////                    )
////                    .httpOnly(true)
////                    .secure(false)
////                    .path("/")
////                    .maxAge(Duration.ofMinutes(30)) // Set expiration time
////                    .build();
////
////            webFilterExchange.getExchange().getResponse().getHeaders().add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
//            // Redirect to SPA Home after successful login
////            URI redirectUri = UriComponentsBuilder.fromUriString("http://localhost:4200/home").build().toUri();
////            RedirectServerAuthenticationSuccessHandler redirectHandler =
////                    new RedirectServerAuthenticationSuccessHandler(redirectUri.toString());
////            return redirectHandler.onAuthenticationSuccess(webFilterExchange, authentication);
//
//
//            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed: "));
//        };
//    }

//    @Bean
//    public ServerAuthenticationFailureHandler authenticationFailureHandler() {
//        return (webFilterExchange, authentication) -> {
////            ResponseCookie accessTokenCookie = ResponseCookie.from(
////                    "access_token",
////                            ((DefaultOidcUser) authentication.getPrincipal()).getIdToken().getTokenValue()
////                    )
////                    .httpOnly(true)
////                    .secure(false)
////                    .path("/")
////                    .maxAge(Duration.ofMinutes(30)) // Set expiration time
////                    .build();
////
////            webFilterExchange.getExchange().getResponse().getHeaders().add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
//            // Redirect to SPA Home after successful login
////            URI redirectUri = UriComponentsBuilder.fromUriString("http://localhost:4200/home").build().toUri();
////            RedirectServerAuthenticationFailureHandler redirectHandler =
////                    new RedirectServerAuthenticationFailureHandler(redirectUri.toString());
//////            return webFilterExchange.getExchange().getResponse().setComplete();
////            return redirectHandler.onAuthenticationFailure(webFilterExchange, authentication);
//
//            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed: "));
//        };
//    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

//    @Bean
//    public ServerOAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver(
//            ReactiveClientRegistrationRepository clientRegistrationRepository) {
//        DefaultServerOAuth2AuthorizationRequestResolver resolver =
//                new DefaultServerOAuth2AuthorizationRequestResolver(clientRegistrationRepository);
////        resolver.setAuthorizationRequestCustomizer(customizer -> customizer
////                .additionalParameters(params -> params.put("code_challenge_method", "S256")));
//        return resolver;
//    }
//
//    @Bean
//    public ReactiveClientRegistrationRepository clientRegistrationRepository() {
//        ClientRegistration keycloakClient = ClientRegistration.withRegistrationId("keycloak")
//                .clientId("frontend-app")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("http://localhost:8080/login/oauth2/code/keycloak")
//                .scope("openid", "profile", "email")
//                .authorizationUri("http://localhost:9080/realms/ecommerce-app/protocol/openid-connect/auth")
//                .tokenUri("http://localhost:9080/realms/ecommerce-app/protocol/openid-connect/token")
//                .userInfoUri("http://localhost:9080/realms/ecommerce-app/protocol/openid-connect/userinfo")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .build();
//
//        return new InMemoryReactiveClientRegistrationRepository(List.of(keycloakClient));
//    }


//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//                // Disable CSRF for APIs
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//
//                // Disable Cors for APIs
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//
//                // Configure authorization rules
//                .authorizeExchange(exchanges -> exchanges
//                        .pathMatchers("/public/**").permitAll()
//                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
//                        .anyExchange().authenticated()
//                )
//                                .oauth2Login(oauth2 -> oauth2
//                        .authorizationEndpoint(auth -> auth
//                                .authorizationRequestResolver(
//                                        new DefaultOAuth2AuthorizationRequestResolver(
//                                                clientRegistrationRepository(), "/oauth2/authorization")
//                                )
//                        )
//                )
//                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder())))
////                .logout(logout -> logout
////                        .logoutUrl("/logout")
////                        .logoutSuccessHandler(new OidcClientInitiatedServerLogoutSuccessHandler(
////                                clientRegistrationRepository))
////                )
//                // Configure OAuth2 Resource Server with JWT
////                .oauth2ResourceServer(oauth2 -> oauth2
////                        .jwt(jwt -> jwt
////                                .jwtAuthenticationConverter(
////                                        new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter()
////                                        )
////                                )
////                        )
////                )
//                ;
//
//        return http.build();
//    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return converter;
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder(@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") String jwkSetUri) {
        NimbusReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();

        jwtDecoder.setJwtValidator(
                new DelegatingOAuth2TokenValidator<>(
                        new JwtTimestampValidator(Duration.ofSeconds(0)) // Set custom skew (30 seconds)
                )
        );
        return jwtDecoder;
    }

//    @Bean
//    public AuthenticationEntryPoint unauthorizedEntryPoint() {
//        return (request, response, authException) -> {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication is required.");
//        };
//    }
//
//    @Bean
//    public AccessDeniedHandler accessDeniedHandler() {
//        return (HttpServletRequest request, HttpServletResponse response,
//                org.springframework.security.access.AccessDeniedException accessDeniedException) -> {
//            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
//        };
//    }
}
