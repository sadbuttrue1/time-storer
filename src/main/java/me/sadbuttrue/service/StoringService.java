package me.sadbuttrue.service;

import lombok.RequiredArgsConstructor;
import me.sadbuttrue.async.manager.TaskManager;
import me.sadbuttrue.async.producer.TimeProducer;
import me.sadbuttrue.repository.TimeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StoringService {

    private final TimeRepository repository;

    private final TaskManager manager;

    private final TimeProducer producer;

    public void printStoredTime() {
        repository.findAll().forEach(time -> System.out.println(time));
    }

    public void startStoringAndWaitForStop() throws IOException {
        manager.setEnabled(true);
        producer.setEnabled(true);

        System.out.println("To exit press ENTER");
        System.in.read();
    }

}
