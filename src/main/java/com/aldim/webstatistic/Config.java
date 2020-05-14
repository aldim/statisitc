package com.aldim.webstatistic;

import com.aldim.webstatistic.model.Visit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class Config {
    @Value("${app.queuesize}")
    private int QUEUE_MAX_SIZE;

    @Bean
    LinkedBlockingQueue<Visit> eventQueue() {
        return (QUEUE_MAX_SIZE == 0)
                ? new LinkedBlockingQueue<>()
                : new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);
    }
}
