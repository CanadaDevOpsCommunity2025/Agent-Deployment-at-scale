package com.example.genai.devops.config;


import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.KeyCredential;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureChatClientConfig {

    @Value("${spring.ai.azure.openai.api-key}")
    private String apiKey;
    @Value("${spring.ai.azure.openai.endpoint}")
    private String endpoint;


    private final AzureOpenAiChatModel azureOpenAiChatModel;
    public AzureChatClientConfig(AzureOpenAiChatModel azureOpenAiChatModel) {
        this.azureOpenAiChatModel = azureOpenAiChatModel;
    }

    @Bean
    public OpenAIClient openAIClient(){
        return new OpenAIClientBuilder()
                .endpoint(endpoint)
                .credential(new KeyCredential(apiKey))
                .buildClient();
    }

    @Bean
    public ChatClient chatClient(){
        return ChatClient.builder(azureOpenAiChatModel).build();
    }
}
