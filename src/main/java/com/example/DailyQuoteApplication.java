package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DailyQuoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyQuoteApplication.class, args);
        System.out.println("Running in port: 8080...");
    }
}
