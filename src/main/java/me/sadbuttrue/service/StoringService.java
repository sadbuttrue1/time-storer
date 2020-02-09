package me.sadbuttrue.service;

import lombok.RequiredArgsConstructor;
import me.sadbuttrue.async.db.DBSaver;
import me.sadbuttrue.async.manager.TaskManager;
import me.sadbuttrue.async.producer.TimeProducer;
import me.sadbuttrue.model.dto.TimeTask;
import me.sadbuttrue.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
public class StoringService {

    private final TimeRepository repository;

    private final TaskManager manager;

    private final TimeProducer producer;

    @Autowired
    private final BlockingQueue<TimeTask> taskQueue = new LinkedBlockingQueue<>();

    public void printStoredTime() {
        repository.findAll().forEach(time -> System.out.println(time));
    }

    public void startStoringAndWaitForStop() throws IOException {
        var saver = new DBSaver(taskQueue, repository);

        var executor = Executors.newSingleThreadExecutor();
        executor.submit(saver);
        manager.setEnabled(true);
        producer.setEnabled(true);

        System.out.println("To exit press ENTER");
        System.in.read();
        executor.shutdown();
    }

}
