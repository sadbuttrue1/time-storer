package me.sadbuttrue.async.db;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;

import me.sadbuttrue.async.dto.TimeTask;
import me.sadbuttrue.repository.TimeRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class DBSaver {
    private final TimeRepository repository;

    private final RetryPolicy<Object> retryPolicy = new RetryPolicy<>()
            .withMaxAttempts(Integer.MAX_VALUE)
            .onFailedAttempt(e -> log.error("Connection attempt failed", e.getLastFailure()))
            .onRetry(e -> log.error("Write to DB failed. Retrying at {}", LocalDateTime.now()))
            .onSuccess(e -> {
                if (e.getAttemptCount() > 1) {
                    log.info("Connection with DB up and running again at {} after {} retries",
                            LocalDateTime.now(), e.getAttemptCount() - 1);
                }
            });

    @Async("singleThreadExecutor")
    public void tryToStore(TimeTask task) {
        Failsafe.with(retryPolicy).run(() -> {
            repository.saveAll(task.getTimes());
        });
    }
}
