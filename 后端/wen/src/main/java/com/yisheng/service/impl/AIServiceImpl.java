package com.yisheng.service.impl;

import com.yisheng.service.AIService;
import com.yisheng.utils.SparkUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class AIServiceImpl implements AIService {

    // private static final Long DEFAULT_TIMEOUT = 60000L;
    private static final Map<String, SseEmitter> map = new HashMap<>();

    public String fetchDataFromPost(String content) throws IOException {
        CloseableHttpClient httpClient = SparkUtil.createHttpClient();
        SseEmitter sseEmitter = map.get("key");
        HttpPost httpPost = SparkUtil.httpPost(content);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info("Received line: {}", line);
                    sseEmitter.send(line);
                }
            }
        }
        EntityUtils.consume(responseEntity);
        return null;
    }

    @Override
    public void streamAIResponse(SseEmitter emitter) {

    }

    @Override
    public SseEmitter createSseEmitter() {
        SseEmitter emitter = new SseEmitter();
        emitter.onTimeout(emitter::complete);
        map.put("key", emitter);
        return emitter;
    }

    // Generate a unique request ID
    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public SseEmitter creatSseEmitter() {
        return createSseEmitter();
    }
}
