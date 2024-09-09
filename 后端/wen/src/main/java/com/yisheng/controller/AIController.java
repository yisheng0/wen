package com.yisheng.controller;

import com.yisheng.pojo.Result;
import com.yisheng.pojo.Session;
import com.yisheng.service.AIService;
import com.yisheng.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private AIService aiService;
    @Autowired
    private SessionService sessionService;
    public String data;

    @PostMapping("/chat")
    public String receiveChat(@RequestBody String content) throws IOException {
        data = aiService.fetchDataFromPost(content);
        return data != null ? "Data received and processed!" : "Failed to fetch data.";
    }

    @GetMapping("/chat-sse")
    public SseEmitter streamAIResponse() throws IOException {
        SseEmitter emitter = aiService.creatSseEmitter();
        aiService.streamAIResponse(emitter);
        return emitter;
    }

    @PostMapping
    public Result addSession(@RequestBody Session session) {
        System.out.println("Received session: " + session);
        String result = sessionService.add(session);
        return Result.success(result);
    }
}
