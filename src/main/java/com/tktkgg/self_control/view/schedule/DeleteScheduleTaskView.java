package com.tktkgg.self_control.view.schedule;

import java.time.DayOfWeek;
import java.util.List;

import com.tktkgg.self_control.model.Schedule;
import com.tktkgg.self_control.model.Task;
import com.tktkgg.self_control.service.ScheduleService;
import com.tktkgg.self_control.service.TaskService;
import com.tktkgg.self_control.util.Input;
import com.tktkgg.self_control.util.InputUtils;
import com.tktkgg.self_control.view.MenuAction;
import com.tktkgg.self_control.view.ViewUtils;

public class DeleteScheduleTaskView implements MenuAction {
	private final ScheduleService ss = new ScheduleService();
	private final TaskService ts = new TaskService();
	
	private Schedule chooseOne() {
		int i = InputUtils.inputWeek();
		if (i == 0) return null;
		
		DayOfWeek day = DayOfWeek.of(i);
		
		Schedule schedule = ss.getSpecificSchedule(day);
		if (schedule == null) {
			System.out.println("スケジュールが存在していません");
			return null;
		}
		
		return schedule;
	}
	
	@Override
	public void execute() {
		System.out.println("スケジュールとタスクのどちらを削除しますか？（1:スケジュール 2:タスク）");
		if (ViewUtils.confirm()) {
			System.out.println("何曜日のスケジュールを削除しますか？（1:月 2:火 3:水 4:木 5:金 6:土 7:日）（0で戻る）");
			
			Schedule schedule = chooseOne();
			if (schedule == null) {
				return;
			}
			
			List<Task> tasks = ts.getTasks(schedule.getId());
			
			ViewUtils.viewTitle(schedule, tasks);
			
			System.out.println("このスケジュールを削除しますか？（1:はい 2:いいえ）");
			if (ViewUtils.confirm("削除")) {
				ss.deleteSchedule(schedule.getId());
			}
			
		} else {
			System.out.println("何曜日のタスクを削除しますか？（1:月 2:火 3:水 4:木 5:金 6:土 7:日）（0で戻る）");
			
			Schedule schedule = chooseOne();
			if (schedule == null) {
				return;
			}
			
			List<Task> tasks = ts.getTasks(schedule.getId());
			
			ViewUtils.viewTaskWithNumber(tasks);
			System.out.println("削除するタスクを選択してください（番号を入力）（0で戻る）");
						
			Task task = null;
			while (true) {
				int num = Input.nextInt();
				
				try {
					task = tasks.get(num - 1);
				} catch (IndexOutOfBoundsException e) {
					System.out.println("存在しない番号です。");
					continue;
				}
				
				if (task == null || !task.belongsTo(schedule)) {
					System.out.println("存在しないタスクです。");
					continue;
				} else {
					break;
				}
			}
			
			System.out.println(task.getTaskName());
			System.out.println(task.getTimeRange());
			System.out.println(task.getMemo());
			System.out.println("このタスクを削除しますか？（1:はい 2:いいえ）");
			
			if (ViewUtils.confirm("削除")) {
				ts.deleteTask(task.getId());
			}
		}
	}
}
