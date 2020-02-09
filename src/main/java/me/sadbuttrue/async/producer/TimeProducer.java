package me.sadbuttrue.async.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;

@Component
@RequiredArgsConstructor
public class TimeProducer {
    private final BlockingQueue<LocalDateTime> timeQueue;

    @Scheduled(fixedRate = 1000L)
    public void run() {
        var time = LocalDateTime.now();
        timeQueue.add(time);
    }
}
