package me.sadbuttrue.async.producer;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeProducer {
    private final BlockingQueue<LocalDateTime> timeQueue;

    @Setter
    private boolean isEnabled = false;

    @Scheduled(fixedRateString = "${me.sadbuttrue.async.producer.TimeProducer.schedulerRate:1000L}")
    public void run() {
        if (isEnabled) {
            var time = LocalDateTime.now();
            timeQueue.add(time);
        }
    }
}
