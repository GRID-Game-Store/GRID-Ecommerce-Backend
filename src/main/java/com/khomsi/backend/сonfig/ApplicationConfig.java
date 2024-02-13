package com.khomsi.backend.сonfig;

import com.khomsi.backend.сonfig.service.PropertiesMessageService;
import com.khomsi.backend.сonfig.service.PropertiesMessageServiceImpl;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Configuration
public class ApplicationConfig {
    @Bean
    public PropertiesMessageService messageService(Environment env) {
        return new PropertiesMessageServiceImpl(env);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/favicon.ico");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(@Value("${app.cors.allowed-origins}")
                                                               List<String> allowedOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components())
                .info(new Info().title("Application GRID REST API")
                        .description("GRID Application that allows CRUD operations")
                        .version("1.0").contact(new Contact().name("Samir Khomsi Kak")));
    }
}
