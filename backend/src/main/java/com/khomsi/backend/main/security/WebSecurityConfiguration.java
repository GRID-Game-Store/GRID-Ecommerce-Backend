package com.khomsi.backend.main.security;

import com.khomsi.backend.main.security.keycloak.KeycloakLogoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.khomsi.backend.main.user.model.entity.ERole.ROLE_USER;


@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final KeycloakLogoutHandler keycloakLogoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChainConfig(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger", "/swagger-ui/**",
                                "/v3/api-docs/**","/error").permitAll()

                        .requestMatchers("/api/v1/games", "/api/v1/games/**",
                                "/api/v1/genres/**", "/api/v1/genres"
                        ).permitAll()

                        .requestMatchers("/api/v1/users/**", "/api/v1/users")
                        .hasAuthority(ROLE_USER.name())
                )
                .csrf(AbstractHttpConfigurer::disable)
                //TODO currently redirect on default auth2 generated page what is not the options
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .logoutSuccessHandler(keycloakLogoutHandler.oidcLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID"))
                .build();
    }

}