package com.tktkgg.self_control.service;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import com.tktkgg.self_control.dao.ScheduleDao;
import com.tktkgg.self_control.exception.DatabaseException;
import com.tktkgg.self_control.model.Schedule;
import com.tktkgg.self_control.model.User;
import com.tktkgg.self_control.util.SessionManager;

public class ScheduleService {
	private final ScheduleDao sd = new ScheduleDao();
	
	private DayOfWeek getDayOfTheWeekShort() { 
		LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        
        return dayOfWeek;
	}
	
	public Schedule getTodaySchedule(){
		try {
			return sd.findByUserIdAndDayOfWeek(SessionManager.getUser().getId(), getDayOfTheWeekShort());
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public Schedule getSpecificSchedule(DayOfWeek day) {
		try {
			return sd.findByUserIdAndDayOfWeek(SessionManager.getUser().getId(), day);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public Schedule getSpecificSchedule(User user, DayOfWeek day) {
		try {
			return sd.findByUserIdAndDayOfWeek(user.getId(), day);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public Schedule getTheSchedule(int id) {
		try {
			return sd.findById(id);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public Schedule createSchedule(Schedule schedule) {
		try {
			return sd.create(schedule);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public void updateSchedule(Schedule schedule) {
		try {
			sd.update(schedule);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public void deleteSchedule(int id) {
		try {
			sd.delete(id);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
}
