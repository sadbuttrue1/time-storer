package me.sadbuttrue.async.producer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;

@Component
@RequiredArgsConstructor
public class TimeProducer {
    private final BlockingQueue<LocalDateTime> timeQueue;

    @Setter
    @Getter
    private boolean isEnabled = false;

    @Scheduled(fixedRateString = "${me.sadbuttrue.async.producer.TimeProducer.schedulerRate:1000}")
    public void produce() {
        if (isEnabled) {
            var time = LocalDateTime.now();
            timeQueue.add(time);
        }
    }
}
