package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DailyQuoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyQuoteApplication.class, args);
        System.out.println("Running in port: 8080...");
    }
}
