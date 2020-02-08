package me.sadbuttrue.service;

import lombok.RequiredArgsConstructor;
import me.sadbuttrue.async.db.DBSaver;
import me.sadbuttrue.async.producer.TimeProducer;
import me.sadbuttrue.repository.TimeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StoringService {

    private final TimeRepository repository;

    private final BlockingQueue<Date> queue = new LinkedBlockingQueue<>();

    public void printStoredTime() {
        repository.findAll().forEach(time -> System.out.println(time));
    }

    public void startStoringAndWaitForStop() throws IOException {
        var producer = new TimeProducer(queue);
        var saver = new DBSaver(queue, repository);

        var executor = Executors.newSingleThreadExecutor();
        executor.submit(saver);

        var scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(producer, 0, 1, TimeUnit.SECONDS);

        System.out.println("To exit press ENTER");
        System.in.read();
        executor.shutdown();
        scheduler.shutdown();
    }

}
