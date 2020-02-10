package me.sadbuttrue.async.manager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import me.sadbuttrue.async.dto.TimeTask;
import me.sadbuttrue.model.Time;

@Component
@RequiredArgsConstructor
public class TaskManager {
    private final BlockingQueue<LocalDateTime> timeQueue;

    private final BlockingQueue<TimeTask> taskQueue;

    @Value("${me.sadbuttrue.async.manager.TaskManager.sendThreshold:10}")
    private int sendThreshold;

    @Setter
    private boolean enabled = false;

    @Scheduled(fixedRateString = "${me.sadbuttrue.async.manager.TaskManager.schedulerRate:100L}")
    public void manage() {
        if (enabled && taskQueue.size() < sendThreshold && !timeQueue.isEmpty()) {
            var times = new ArrayList<LocalDateTime>();
            timeQueue.drainTo(times);
            List<Time> preparedForSaving = times.stream()
                    .map(time -> Time.builder().time(time).build())
                    .collect(Collectors.toList());
            taskQueue.add(TimeTask.builder().times(preparedForSaving).build());
        }
    }
}
