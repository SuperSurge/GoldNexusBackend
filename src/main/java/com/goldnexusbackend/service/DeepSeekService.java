package com.goldnexusbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class DeepSeekService {
    private static final String API_URL="https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY="sk-3c42ab0be36847128583437bb46322f1";

    public String chat(String userMessage){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String,Object> requestBody = new HashMap<>();

        requestBody.put("model","deepseek-chat");

        List<Map<String,String>> messages = new ArrayList<>();

        Map<String,String> system = new HashMap<>();
        system.put("role","system");
        system.put("content","你是金融个人借贷平台风控平台的AI客服");

        Map<String,String> user = new HashMap<>();
        user.put("role","user");
        user.put("content",userMessage);

        messages.add(system);
        messages.add(user);

        requestBody.put("messages",messages);

        HttpEntity<Map<String,Object>> request =
                new HttpEntity<>(requestBody,headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(API_URL,request,Map.class);

        Map body = response.getBody();

        System.out.println("DeepSeek返回："+body);

        if(body == null){
            return "AI服务异常";
        }

        List choices = (List) body.get("choices");

        if(choices == null || choices.isEmpty()){
            return "AI没有返回结果";
        }

        Map choice = (Map) choices.get(0);

        Map message = (Map) choice.get("message");

        if(message == null){
            return "AI返回格式错误";
        }

        return (String) message.get("content");
    }
}
