package com.tktkgg.self_control.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.tktkgg.self_control.model.Schedule;
import com.tktkgg.self_control.model.Task;

public class AlarmService {
	private final ScheduleService ss = new ScheduleService();
	private final TaskService ts = new TaskService();
	
	public Optional<Task> checkAlarm() {
		Schedule schedule = ss.getTodaySchedule();
		List<Task> tasks = ts.getTasks(schedule.getId());
		
		LocalTime currentTime = LocalTime.now();
		currentTime.truncatedTo(ChronoUnit.SECONDS);
		
		for (Task task : tasks) {
			LocalTime taskTime = task.getStartTime();
			
			if (taskTime.equals(currentTime)) {
				return Optional.ofNullable(task);
			}
		}
		
		return null;
	}
}
