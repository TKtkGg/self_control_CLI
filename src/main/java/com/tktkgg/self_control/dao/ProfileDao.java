package com.tktkgg.self_control.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tktkgg.self_control.model.Profile;
import com.tktkgg.self_control.util.DBConnection;

public class ProfileDao {
	
	private Profile mapProfile(ResultSet rs) throws SQLException {
		return new Profile(
			rs.getInt("user_id"),
			rs.getString("bio"),
			rs.getString("job"),
			rs.getInt("age")
		);
	}
	
	public Profile findByUserId(int userId) throws SQLException {
		String sql = "SELECT * FROM profiles WHERE user_id = ?";
		
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setInt(1, userId);
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapProfile(rs);
				}
				
				return null;
			}
		}
	}
	
	public void create(Profile profile) throws SQLException {
		String sql = "INSERT INTO profiles (user_id, bio, job, age) VALUES (?, ?, ?, ?)";
		
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setInt(1, profile.getUserId());
			pstmt.setString(2, profile.getBio());
			pstmt.setString(3, profile.getJob());
			pstmt.setInt(4, profile.getAge());
			
			int count = pstmt.executeUpdate();
			if (count == 0) {
				throw new SQLException("作成できませんでした");
			}
		}
	}
	
	public void update(Profile profile) throws SQLException {
		String sql = "UPDATE profiles SET bio = ?, job = ?, age = ? WHERE user_id = ?";
		
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setString(1, profile.getBio());
			pstmt.setString(2, profile.getJob());
			pstmt.setInt(3, profile.getAge());
			pstmt.setInt(4, profile.getUserId());
			
			int count = pstmt.executeUpdate();
			if (count == 0) {
				throw new SQLException("更新できませんでした");
			}
		}
	}
	
	public void delete(int userId) throws SQLException {
		String sql = "DELETE FROM profiles WHERE user_id = ?";
		
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			pstmt.setInt(1, userId);
			
			int count = pstmt.executeUpdate();
			if (count == 0) {
				throw new SQLException("削除できませんでした");
			}
		}
	}
}
