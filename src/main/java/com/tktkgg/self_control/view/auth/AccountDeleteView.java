package com.tktkgg.self_control.view.auth;

import com.tktkgg.self_control.service.UserService;
import com.tktkgg.self_control.util.SessionManager;
import com.tktkgg.self_control.view.MenuAction;
import com.tktkgg.self_control.view.ViewUtils;

public class AccountDeleteView implements MenuAction {
	private final UserService us = new UserService();
	
	@Override
	public void execute() {
		System.out.println("本当にこのアカウントを削除してよろしいですか？(1:はい 2:いいえ)");
		if (ViewUtils.confirm("削除")) {
			us.deleteUser(us.getCurrentUser().getId());
			SessionManager.clearUser();
		}
	}

}
