package me.sadbuttrue.async.db;

import lombok.RequiredArgsConstructor;
import me.sadbuttrue.model.Time;
import me.sadbuttrue.repository.TimeRepository;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DBSaver implements Runnable {
    private Logger logger = LoggerFactory.getLogger(DBSaver.class);

    private final BlockingQueue<Date> timeQueue;

    private final TimeRepository repository;

    private final RetryPolicy<Object> retryPolicy = new RetryPolicy<>()
            .withMaxAttempts(Integer.MAX_VALUE)
//            not needed, if enabled it'll actually give timeout of mongo timeout (5s) + itself (5s)
//            .withDelay(Duration.ofSeconds(5L))
            .onFailedAttempt(e -> logger.error("Connection attempt failed", e.getLastFailure()))
            .onRetry(e -> logger.error("Failure #{}. Retrying at {}", e.getAttemptCount(), new Date()));

    private final Queue<Date> localTimes = new LinkedList<>();

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            tryToStore();
        }
    }

    private void tryToStore() {
        Failsafe.with(retryPolicy).run(() -> {
            if (!timeQueue.isEmpty()) {
                timeQueue.drainTo(localTimes);
                var preparedFofSaving = localTimes.stream()
                        .map(time -> Time.builder().time(time).build())
                        .collect(Collectors.toList());
                repository.saveAll(preparedFofSaving);
                localTimes.clear();
            }
        });
    }
}
