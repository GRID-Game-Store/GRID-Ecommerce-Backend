package com.khomsi.grid.сonfig;

import com.khomsi.grid.сonfig.service.PropertiesMessageService;
import com.khomsi.grid.сonfig.service.PropertiesMessageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig {
    @Bean
    public PropertiesMessageService messageService(Environment env) {
        return new PropertiesMessageServiceImpl(env);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*").allowedHeaders("*");
            }
        };
    }
}
