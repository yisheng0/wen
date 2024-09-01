package com.yisheng.controller;

import com.yisheng.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private AIService aiService;



    @PostMapping("/chat")
    public SseEmitter streamAIResponse(String content){
        // 创建对象
        SseEmitter emitter = aiService.creatSseEmitter();

        try {
            aiService.streamAIResponse(emitter , content);
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
        return emitter;

    }
}
