package me.sadbuttrue.async.submitter;

import java.util.concurrent.BlockingQueue;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import me.sadbuttrue.async.db.DBSaver;
import me.sadbuttrue.model.dto.TimeTask;

@Component
@RequiredArgsConstructor
public class TaskSubmitter {
	private Logger logger = LoggerFactory.getLogger(TaskSubmitter.class);

	private final BlockingQueue<TimeTask> taskQueue;

	private final DBSaver saver;


	@Scheduled(fixedRate = 100L)
	public void submit() {
		if (!taskQueue.isEmpty()) {
			var task = taskQueue.peek();
			try {
				saver.tryToStore(task);
				taskQueue.take();
			} catch (TaskRejectedException e) {
				logger.info("Task {} was rejected by saver. Will try again", task, e);
			} catch (InterruptedException e) {
				logger.error("Scheduled submitter was interrupted", e);
			}
		}
	}
}
