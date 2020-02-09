package me.sadbuttrue;

import me.sadbuttrue.model.dto.TimeTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class TimeStorer {

    @Bean
    public BlockingQueue<LocalDateTime> createTimeQueue() {
        return new LinkedBlockingQueue<>();
    }

    @Bean
    public BlockingQueue<TimeTask> createTaskQueue() {
        return new LinkedBlockingQueue<>();
    }

    @Bean("singleThreadExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.initialize();
        return executor;
    }

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(TimeStorer.class, args)));
    }
}
