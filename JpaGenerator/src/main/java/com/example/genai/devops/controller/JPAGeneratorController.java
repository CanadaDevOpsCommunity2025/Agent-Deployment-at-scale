package com.example.genai.devops.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1")
public class JPAGeneratorController {

    @Value("${spring.ai.azure.openai.chat.options.deployment-name}")
    private String deploymentName;

    private final ChatClient chatClient;

    public JPAGeneratorController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @PostMapping
    public String getJPAFromPrompt(@RequestParam String agentPrompt){

        return chatClient
                .prompt(loadPromptFile("/system_prompt.txt"))
                .user(agentPrompt)
                .call()
                .content();
    }

    private String loadPromptFile(String fileName){
        try (InputStream is = getClass().getResourceAsStream(fileName)){
            if (is == null) {
                throw new RuntimeException("File not found: " + fileName);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
