package me.sadbuttrue.service;

import lombok.RequiredArgsConstructor;
import me.sadbuttrue.async.manager.TaskManager;
import me.sadbuttrue.async.producer.TimeProducer;
import me.sadbuttrue.repository.TimeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoringService {

    private final TimeRepository repository;

    private final TaskManager manager;

    private final TimeProducer producer;

    private final ApplicationContext context;

    public void printStoredTime() {
        repository.findAll().forEach(time -> System.out.println(time));
        System.exit(SpringApplication.exit(context));
    }

    public void startStoringAndWaitForStop() {
        manager.setEnabled(true);
        producer.setEnabled(true);
    }

}
