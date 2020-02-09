package me.sadbuttrue.async.producer;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;

@AllArgsConstructor
public class TimeProducer implements Runnable {
    private BlockingQueue<LocalDateTime> timeQueue;

    public void run() {
        var time = LocalDateTime.now();
        timeQueue.add(time);
    }
}
