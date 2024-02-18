package com.khomsi.backend.сonfig;

import com.khomsi.backend.сonfig.service.PropertiesMessageService;
import com.khomsi.backend.сonfig.service.PropertiesMessageServiceImpl;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
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
    public static final String BEARER_KEY_SECURITY_SCHEME = "bearer-key";
    @Bean
    public PropertiesMessageService messageService(Environment env) {
        return new PropertiesMessageServiceImpl(env);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(@Value("${app.front-url}")
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
        return new OpenAPI().components(new Components()
                .addSecuritySchemes(BEARER_KEY_SECURITY_SCHEME,
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info().title("Application GRID REST API")
                        .description("GRID Application that allows CRUD operations")
                        .version("1.0").contact(new Contact().name("Samir Khomsi Kak")));
    }
    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder().group("api").pathsToMatch("/api/**").build();
    }

    @Bean
    public GroupedOpenApi actuatorApi() {
        return GroupedOpenApi.builder().group("actuator").pathsToMatch("/actuator/**").build();
    }

}
