package com.yisheng.config;

import io.github.briqt.spark4j.SparkClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Data
//@Configuration
//@ConfigurationProperties(prefix = "yisheng.spark")
//public class SparkConfig {
//
//    private String appId;
//    private String apiSecret;
//    private String apiKey;
//
//    @Bean
//    public SparkClient sparkClient(){
//        SparkClient sparkClient = new SparkClient();
//        sparkClient.appid = appId;
//        sparkClient.apiSecret = apiSecret;
//        sparkClient.apiKey = apiKey;
//        return sparkClient;
//    }
//}
