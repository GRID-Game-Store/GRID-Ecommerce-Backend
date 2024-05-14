package com.khomsi.backend.сonfig;

import com.khomsi.backend.сonfig.service.PropertiesMessageService;
import com.khomsi.backend.сonfig.service.PropertiesMessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
