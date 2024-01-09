package com.khomsi.grid.сonfig;

import com.khomsi.grid.сonfig.service.PropertiesMessageService;
import com.khomsi.grid.сonfig.service.PropertiesMessageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfig {
    @Bean
    public PropertiesMessageService messageService(Environment env) {
        return new PropertiesMessageServiceImpl(env);
    }
}
