package com.yisheng.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SparkUtil {

    private static final String url = "https://spark-api-open.xf-yun.com/v1/chat/completions";
    private static final String contentType = "application/json";
    private static final String authorization = "Bearer pGlPicxfjgILKlpQrEZF:NhAyoLtQByDLDoUPrQHI";
    public static CloseableHttpClient createHttpClient(){
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setSocketTimeout(10000)
                        .setConnectTimeout(10000)
                        .build())
                .build();
        return httpClient;
    }

    public static HttpPost httpPost(String content) {
//        System.out.println(content);
//        JsonParser parser = new JsonParser();
//        JsonElement jsonElement = parser.parse(content);
//        System.out.println(content);
//        System.out.println(jsonElement);
//        String contentValue = null;
//        if (jsonElement.isJsonObject()) {
//            JsonObject jsonObject = jsonElement.getAsJsonObject();
//            contentValue = jsonObject.get("content").getAsString();
//            System.out.println(contentValue);
//        } else {
//            System.out.println("Invalid JSON format");
//        }

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", contentType);
        httpPost.setHeader("Authorization", authorization); // 注意替换成你的API密钥
        String json = "{\"model\":\"4.0Ultra\",\"messages\":" + content.toString() + ",\"stream\":true}";
        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        httpPost.setEntity(entity);
        return httpPost;
    }

    public void sendMessage(){
//        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
//            HttpEntity responseEntity = response.getEntity();
//            if (responseEntity != null) {
//                // 假设返回的是流式JSON（这里可能需要根据实际情况处理）
//                // 但通常SSE是通过文本行发送的，这里为了示例简化处理
//                try (BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        System.out.println(line);
//                    }
//                }
//            }
//            EntityUtils.consume(responseEntity); // 消耗响应内容
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        // 创建HttpClient实例


        // 创建HttpPost实例


        // 设置请求头


        // 设置请求体（JSON格式）


//        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
//            HttpEntity responseEntity = response.getEntity();
//            if (responseEntity != null) {
//                // 创建SseEmitter实例
//                SseEmitter emitter = createSseEmitter();
//                try (BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        // 发送消息到前端
//                        emitter.send(line);
//                    }
//                } catch (IOException e) {
//                    emitter.completeWithError(e); // 发生错误时完成并发送异常信息
//                } finally {
//                    emitter.complete(); // 读取完成后关闭连接
//                }
//            }
//            EntityUtils.consume(responseEntity); // 消耗响应内容
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
