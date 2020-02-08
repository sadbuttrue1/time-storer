package me.sadbuttrue.async.producer;

import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

@AllArgsConstructor
public class TimeProducer implements Runnable {
    private BlockingQueue<Date> timeQueue;

    public void run() {
        var time = new Date();
        timeQueue.add(time);
    }
}
