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
        system.put("content",
                "你是一个专业的金融借贷风控平台AI客服。\n" +
                        "平台名称：金纽贷（GoldNexusLoan）\n" +
                        "平台功能包括：用户借贷申请、信用评估、风控审核、还款管理。\n" +
                        "风控规则：\n" +
                        "1. 信用分低于600拒绝\n" +
                        "2. 有逾期记录需人工审核\n" +
                        "3. 单笔借款上限10万\n" +
                        "回答要求：\n" +
                        "1. 必须基于平台规则回答\n" +
                        "2. 不确定的信息不要编造\n" +
                        "3. 用客服语气，简洁清晰"
        );

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

        //System.out.println("DeepSeek返回："+body);

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
