package com.tktkgg.self_control.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.tktkgg.self_control.dao.ScheduleDao;
import com.tktkgg.self_control.dao.TaskDao;
import com.tktkgg.self_control.exception.DatabaseException;
import com.tktkgg.self_control.exception.InvalidTimeException;
import com.tktkgg.self_control.model.Schedule;
import com.tktkgg.self_control.model.Task;
import com.tktkgg.self_control.util.DBConnection;

public class ScheduleTaskService {
	private final ScheduleDao sd = new ScheduleDao();
	private final TaskDao td = new TaskDao();
	
	public void createSchedule(Schedule schedule, Task task) throws InvalidTimeException {
		if (!task.isTimeValid()) {
			throw new InvalidTimeException("無効な時間です");
		}
		Connection con = null;
		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false);
			
			Schedule newSchedule = sd.create(schedule, con);
			task.setScheduleId(newSchedule.getId());
			td.create(task, con);
			
			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				ConnectionService.rollback(con);
			}
			throw new DatabaseException(e);
			
		} finally {
			if (con != null) {
				ConnectionService.close(con);
			}
		}
		
	}
	
	public void updateSchedule(Schedule schedule, Task task) throws InvalidTimeException {
		if (!task.isTimeValid()) {
			throw new InvalidTimeException("無効な時間です");
		}
		
		Connection con = null;
		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false);
			
			sd.update(schedule, con);
			td.update(task, con);
			
			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				ConnectionService.rollback(con);
			}
			throw new DatabaseException(e);
			
		} finally {
			if (con != null) {
				ConnectionService.close(con);
			}
		}
	}
}
