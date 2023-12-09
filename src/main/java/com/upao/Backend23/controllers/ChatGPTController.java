package com.upao.Backend23.controllers;

import com.upao.Backend23.services.ChatGPTService;
import com.upao.Backend23.models.ChatGPTRequest;
import com.upao.Backend23.models.ChatGPTResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/chat")
public class ChatGPTController {
    private ChatGPTService chatGPTService = new ChatGPTService();

    public void getResponse(String input) {
        try {
            ChatGPTResponse response = chatGPTService.sendMessage(new ChatGPTRequest(input));
            System.out.println("Respuesta de ChatGPT: " + response.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

