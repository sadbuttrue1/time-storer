package me.sadbuttrue.async.db;

import lombok.RequiredArgsConstructor;
import me.sadbuttrue.model.dto.TimeTask;
import me.sadbuttrue.repository.TimeRepository;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DBSaver {
    private Logger logger = LoggerFactory.getLogger(DBSaver.class);

    private final TimeRepository repository;

    private final RetryPolicy<Object> retryPolicy = new RetryPolicy<>()
            .withMaxAttempts(Integer.MAX_VALUE)
//            not needed, if enabled it'll actually give timeout of mongo timeout (5s) + itself (5s)
//            .withDelay(Duration.ofSeconds(5L))
            .onFailedAttempt(e -> logger.error("Connection attempt failed", e.getLastFailure()))
            .onRetry(e -> logger.error("Write to DB failed. Retrying at {}", LocalDateTime.now()))
            .onSuccess(e -> {
                if (e.getAttemptCount() > 1) {
                    logger.info("Connection with DB up and running again at {} after {} retries",
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
