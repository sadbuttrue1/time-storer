package me.sadbuttrue;

import me.sadbuttrue.model.dto.TimeTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@EnableScheduling
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

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(TimeStorer.class, args)));
    }
}
