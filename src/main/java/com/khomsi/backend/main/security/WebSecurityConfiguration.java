package com.khomsi.backend.main.security;

import com.khomsi.backend.main.security.keycloak.JwtAuthConverter;
import com.khomsi.backend.main.user.model.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChainConfig(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger", "/swagger-ui/**",
                                //todo remove email link
                                "/v3/api-docs/**", "/error", "/api/v1/email/**").permitAll()

                        .requestMatchers("/api/v1/games", "/api/v1/games/**",
                                "/api/v1/genres/**", "/api/v1/genres",
                                "/api/v1/platforms", "/api/v1/developers",
                                "/api/v1/publishers", "/api/v1/tags"
                        ).permitAll()

                        .requestMatchers("/api/v1/users/**", "/api/v1/users",
                                "/api/v1/cart/**", "/api/v1/cart",
                                "/api/v1/checkout", "/api/v1/checkout/**",
                                "/api/v1/transactions", "/api/v1/transactions/**"
                        )
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                )
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .build();
    }
}