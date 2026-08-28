package com.tktkgg.self_control.view.auth;

import com.tktkgg.self_control.service.AuthService;
import com.tktkgg.self_control.util.SessionManager;
import com.tktkgg.self_control.view.MenuAction;

public class LogoutView implements MenuAction {
	private final AuthService as = new AuthService();
	
	@Override
	public void execute() {
		as.logout(SessionManager.getUser());
		System.out.println("ログアウトしました");
	}
}
