package com.tktkgg.self_control.alarm;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tktkgg.self_control.service.AlarmService;

public class AlarmManager {
	private final AlarmService as = new AlarmService();
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	
	public void start() {
		scheduler.scheduleAtFixedRate(
	        as::checkAlarm,
	        0,
	        1,
	        TimeUnit.SECONDS
		);
	}
	
	public void stop() {
		scheduler.shutdown();
	}
}
