package com.tktkgg.self_control.service;

import java.sql.SQLException;

import com.tktkgg.self_control.dao.ProfileDao;
import com.tktkgg.self_control.exception.DatabaseException;
import com.tktkgg.self_control.model.Profile;

public class ProfileService {
	private final ProfileDao pd = new ProfileDao();
	
	public Profile getProfile(int userId) {
		try {
			return pd.findByUserId(userId);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	public void createProfile(int userId, String bio, String job, int age) {
		try {
			pd.create(new Profile(userId, bio, job, age));
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public void updateProfile(Profile profile) {
		try {
			pd.update(profile);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public void deleteProfile(int userId) {
		try {
			pd.delete(userId);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
}
