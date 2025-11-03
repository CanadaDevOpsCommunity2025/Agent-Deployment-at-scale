package com.example.genai.devops.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1")
public class SpPromptController {

    @Value("${spring.ai.azure.openai.chat.options.deployment-name}")
    private String deploymentName;

    private final ChatClient chatClient;

    private final RestTemplate restTemplate;

    public SpPromptController(ChatClient chatClient, RestTemplate restTemplate) {
        this.chatClient = chatClient;
        this.restTemplate = restTemplate;
    }


    @PostMapping
    public String getPromptFromSp(@RequestParam String storedProd){

        var respPrompt = chatClient
                .prompt(loadPromptFile("/system_prompt.txt"))
                .user(storedProd)
                .call()
                .content();


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/x-www-form-urlencoded");
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("agentPrompt",respPrompt);
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(map, headers);

        return restTemplate.exchange(
                "https://spring-boot-generator-gea7cfhxedfehjcv.canadacentral-01.azurewebsites.net/api/v1",
                HttpMethod.POST,
                request,
                String.class).getBody();


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
