package com.quid.inserter.Pay.config;

import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutorBuilder()
            .corePoolSize(2)
            .maxPoolSize(2)
            .queueCapacity(100)
            .threadNamePrefix("pay-")
            .build();
        executor.initialize();
        return executor;
    }
}
