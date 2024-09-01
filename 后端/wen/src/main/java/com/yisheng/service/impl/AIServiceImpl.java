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

@Service
@Slf4j
public class AIServiceImpl implements AIService {

    private static final Long DEFAULT_TIMEOUT = 30000L; // 默认超时时间（毫秒）
    @Override
    public void streamAIResponse(SseEmitter emitter, String content) {

        CloseableHttpClient httpClient = SparkUtil.createHttpClient();
        HttpPost httpPost = SparkUtil.httpPost(content);

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 假设返回的是流式JSON（这里可能需要根据实际情况处理）
                // 但通常SSE是通过文本行发送的，这里为了示例简化处理
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        emitter.send(line);
                    }
                }
            }
            EntityUtils.consume(responseEntity); // 消耗响应内容
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public SseEmitter creatSseEmitter() {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitter.onTimeout(() -> emitter.complete()); // 设置超时回调
        return emitter;
    }


}
