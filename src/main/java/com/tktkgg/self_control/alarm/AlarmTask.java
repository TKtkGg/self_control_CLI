package com.tktkgg.self_control.alarm;

import com.tktkgg.self_control.service.AlarmService;

public class AlarmTask implements Runnable {
	private final AlarmService as;
	
	public AlarmTask(AlarmService as) {
		this.as = as;
	}
	
	@Override
	public void run() {
		as.checkAlarm();
	}
	
	public void showAlarm() {
		as.checkAlarm().ifPresent(task -> {
			System.out.println("================================");
            System.out.println("予定の時間です！");
            System.out.println(task.getTaskName());
            System.out.println(task.getTimeRange());
            System.out.println("================================");
		});
	}
}
