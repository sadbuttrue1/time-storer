package me.sadbuttrue.configuration;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import me.sadbuttrue.execution.handler.BlockCallerExecutionPolicy;
import me.sadbuttrue.model.dto.TimeTask;

@Configuration
public class TimeStorerConfiguration {

	@Value("${me.sadbuttrue.saver.queueMaxSize:5}")
	private int queueMaxSize;

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
		executor.setQueueCapacity(queueMaxSize);
		executor.setRejectedExecutionHandler(new BlockCallerExecutionPolicy());
		executor.initialize();
		return executor;
	}
}
