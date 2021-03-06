package me.sadbuttrue.async.submitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sadbuttrue.async.db.DBSaver;
import me.sadbuttrue.async.dto.TimeTask;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskSubmitter {
	private final BlockingQueue<TimeTask> taskQueue;

	private final DBSaver saver;

	@Scheduled(fixedRateString = "${me.sadbuttrue.async.submitter.TaskSubmitter.schedulerRate:100}")
	public void submit() {
		TimeTask task = taskQueue.peek();
		if (task != null) {
			try {
				saver.tryToStore(task);
				taskQueue.take();
			} catch (TaskRejectedException e) {
				log.info("Task {} was rejected by saver. Will try again", task, e);
			} catch (InterruptedException e) {
				log.error("Scheduled submitter was interrupted", e);
			}
		}
	}
}
