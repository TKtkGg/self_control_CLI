package com.tktkgg.self_control.alarm;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tktkgg.self_control.service.AlarmService;

public class AlarmManager {
	private final AlarmService as;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	
	public AlarmManager(AlarmService as) {
		this.as = as;
	}
	
	public void start() {
		scheduler.scheduleAtFixedRate(
	        new AlarmTask(as),
	        0,
	        1,
	        TimeUnit.SECONDS
		);
	}
	
	public void stop() {
		scheduler.shutdown();
	}
}
