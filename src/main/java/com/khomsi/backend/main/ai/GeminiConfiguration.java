package com.khomsi.backend.main.ai;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration(proxyBeanMethods = false)
public class GeminiConfiguration {
    @Value("${app.googleai.api.projectId}")
    private String projectId;
    @Value("${app.googleai.api.location}")
    private String location;
    @Value("${app.googleai.api.model}")
    private String modelName;
    @Bean
    public VertexAI vertexAI() {
        return new VertexAI(projectId, location);
    }
    @Bean
    public GenerativeModel geminiProGenerativeModel(VertexAI vertexAI) {
        return new GenerativeModel(modelName,
                vertexAI);
    }
    @Bean
    @Scope
    public ChatSession chatSession(@Qualifier("geminiProGenerativeModel") GenerativeModel generativeModel) {
        return new ChatSession(generativeModel);
    }
}