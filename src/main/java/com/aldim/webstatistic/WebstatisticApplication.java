package com.aldim.webstatistic;

import com.aldim.webstatistic.model.Visit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.LinkedBlockingQueue;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class WebstatisticApplication {
    @Bean
    LinkedBlockingQueue<Visit> eventQueue() {
        return new LinkedBlockingQueue<>();

    }

    public static void main(String[] args) {
        SpringApplication.run(WebstatisticApplication.class, args);
    }

}
