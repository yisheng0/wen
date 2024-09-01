package com.yisheng;

import io.github.briqt.spark4j.SparkClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BigEventApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigEventApplication.class, args);
    }

}
