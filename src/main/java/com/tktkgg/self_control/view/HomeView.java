package com.tktkgg.self_control.view;

import java.util.HashMap;
import java.util.Map;

import com.tktkgg.self_control.util.Input;
import com.tktkgg.self_control.util.SessionManager;
import com.tktkgg.self_control.view.auth.AccountDeleteView;
import com.tktkgg.self_control.view.auth.LogoutView;
import com.tktkgg.self_control.view.schedule.AddScheduleView;
import com.tktkgg.self_control.view.schedule.CheckScheduleView;
import com.tktkgg.self_control.view.schedule.DeleteScheduleTaskView;
import com.tktkgg.self_control.view.schedule.EditScheduleView;
import com.tktkgg.self_control.view.user.EditProfileView;
import com.tktkgg.self_control.view.user.UsersView;

public class HomeView {
	Map<Integer, MenuAction> menu = new HashMap<>();
	
	public HomeView() {
		menu.put(1, new CheckScheduleView(true));
		menu.put(2, new CheckScheduleView(false));
		menu.put(3, new EditScheduleView());
		menu.put(4, new AddScheduleView());
		menu.put(5, new DeleteScheduleTaskView());
		menu.put(6, new UsersView());
		menu.put(7, new EditProfileView());
		menu.put(8, new LogoutView());
		menu.put(9, new AccountDeleteView());
	}
	
	public void homeView() {
		while(true) {
			System.out.println("ホーム");
			System.out.println(""
					+ "1.今日のスケジュールの確認"
					+ "\n2.スケジュールの確認"
					+ "\n3.スケジュールの編集"
					+ "\n4.スケジュールの追加"
					+ "\n5.スケジュール/タスクの削除"
					+ "\n6.ユーザー一覧"
					+ "\n7.プロフィール編集"
					+ "\n8.ログアウト"
					+ "\n9.アカウント削除");
			
			menu.get(Input.nextInt()).execute();
			
			if (SessionManager.getUser() == null) {
				break;
			}
		}
		
			
	}
}
