package com.tktkgg.self_control.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.tktkgg.self_control.exception.DatabaseException;

public class ConnectionService {
	public static void rollback(Connection con) {
		if (con == null) return;
		
		try {
			con.rollback();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public static void close(Connection con) {
		if (con == null) return;
		
		try {
			con.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
}
