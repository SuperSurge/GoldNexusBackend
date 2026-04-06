package com.goldnexusbackend.controller;

import com.goldnexusbackend.entity.ChatRequest;
import com.goldnexusbackend.entity.ChatResponse;
import com.goldnexusbackend.service.DeepSeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goldnexus/user")
public class ChatController {

    @Autowired
    private DeepSeekService deepSeekService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest chatRequest){
        String reply = deepSeekService.chat(chatRequest.getMessage());
        return new ChatResponse(reply);
    }
}
