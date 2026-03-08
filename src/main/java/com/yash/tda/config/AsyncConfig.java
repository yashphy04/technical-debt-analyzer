package com.yash.tda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync   // ⭐ VERY IMPORTANT
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);       // minimum threads
        executor.setMaxPoolSize(10);       // max threads
        executor.setQueueCapacity(50);     // queue limit
        executor.setThreadNamePrefix("TDA-Async-");

        executor.initialize();

        return executor;
    }
}