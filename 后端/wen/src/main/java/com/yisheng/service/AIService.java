package com.yisheng.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AIService {


    void streamAIResponse(SseEmitter emitter , String content);

    SseEmitter creatSseEmitter();

}
