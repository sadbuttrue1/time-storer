package me.sadbuttrue.async.manager;

import lombok.RequiredArgsConstructor;
import me.sadbuttrue.model.Time;
import me.sadbuttrue.model.dto.TimeTask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskManager {

    private final BlockingQueue<LocalDateTime> timeQueue;

    private final BlockingQueue<TimeTask> taskQueue;

    private static final int SEND_THRESHOLD = 10;

    @Scheduled(fixedRate = 100L)
    public void manage() {
        if (taskQueue.size() < SEND_THRESHOLD && !timeQueue.isEmpty()) {
            var times = new ArrayList<LocalDateTime>();
            timeQueue.drainTo(times);
            List<Time> preparedForSaving = times.stream()
                    .map(time -> Time.builder().time(time).build())
                    .collect(Collectors.toList());
            taskQueue.add(TimeTask.builder().times(preparedForSaving).build());
        }
    }
}
