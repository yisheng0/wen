package com.yisheng.controller;

import com.yisheng.pojo.Result;
import com.yisheng.pojo.Session;
import com.yisheng.pojo.SessionRequest;
import com.yisheng.service.AIService;
import com.yisheng.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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
    public SseEmitter streamAIResponse() {
        SseEmitter emitter = aiService.creatSseEmitter();
        aiService.streamAIResponse(emitter);
        return emitter;
    }

    @PostMapping
    public Result addSession(@RequestBody String content) {
        Session session = new Session();
        session.setContent(content);
        String result = sessionService.add(session);
        return Result.success(result);
    }

    @GetMapping
    public Result getSession() {
        List<Session> session = sessionService.findById();
        return Result.success(session);
    }

    @PutMapping
    public Result updateSession(@RequestBody SessionRequest sessionRequest) {
        Integer id = sessionRequest.getId();
        String content = sessionRequest.getContent();
//        System.out.println(id);
//        System.out.println(content);
        sessionService.update(id, content);
        return Result.success();
    }
}
