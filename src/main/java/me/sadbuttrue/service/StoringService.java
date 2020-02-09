package me.sadbuttrue.service;

import lombok.RequiredArgsConstructor;
import me.sadbuttrue.async.db.DBSaver;
import me.sadbuttrue.async.manager.TaskManager;
import me.sadbuttrue.async.producer.TimeProducer;
import me.sadbuttrue.model.dto.TimeTask;
import me.sadbuttrue.repository.TimeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StoringService {

    private final TimeRepository repository;

    private final BlockingQueue<LocalDateTime> timeQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<TimeTask> taskQueue = new LinkedBlockingQueue<>();

    public void printStoredTime() {
        repository.findAll().forEach(time -> System.out.println(time));
    }

    public void startStoringAndWaitForStop() throws IOException {
        var producer = new TimeProducer(timeQueue);
        var manager = new TaskManager(timeQueue, taskQueue);
        var saver = new DBSaver(taskQueue, repository);

        var executor = Executors.newSingleThreadExecutor();
        executor.submit(saver);

        var scheduler = Executors.newScheduledThreadPool(2);
        scheduler.scheduleAtFixedRate(producer, 0, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(manager, 0, 100, TimeUnit.MILLISECONDS);

        System.out.println("To exit press ENTER");
        System.in.read();
        executor.shutdown();
        scheduler.shutdown();
    }

}
