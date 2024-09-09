package com.yisheng.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public interface AIService {


    void streamAIResponse(SseEmitter emitter);

    SseEmitter createSseEmitter();

    SseEmitter creatSseEmitter();

    String fetchDataFromPost(String content) throws IOException;
}
