package me.sadbuttrue.configuration;

import me.sadbuttrue.async.dto.TimeTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
@EnableScheduling
@EnableAsync
public class TimeStorerConfiguration {
	@Value("${me.sadbuttrue.async.manager.TaskManager.sendThreshold:10}")
	private int taskQueueCapacity;

	@Value("${me.sadbuttrue.async.db.DBSaver.queueCapacity:10}")
	private int saverQueueCapacity;

	@Bean
	public BlockingQueue<LocalDateTime> createTimeQueue() {
		return new LinkedBlockingQueue<>();
	}

	@Bean
	public BlockingQueue<TimeTask> createTaskQueue() {
		return new LinkedBlockingQueue<>(taskQueueCapacity);
	}

	@Bean("singleThreadExecutor")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(saverQueueCapacity);
		executor.initialize();
		return executor;
	}
}
