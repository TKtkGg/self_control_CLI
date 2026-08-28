package com.tktkgg.self_control;

import com.tktkgg.self_control.alarm.AlarmManager;
import com.tktkgg.self_control.exception.DatabaseException;
import com.tktkgg.self_control.service.AlarmService;
import com.tktkgg.self_control.util.Input;
import com.tktkgg.self_control.view.HomeView;
import com.tktkgg.self_control.view.auth.AuthView;

public class Main {
	public static void main(String[] args) {
		AuthView authView = new AuthView();
		HomeView homeView = new HomeView();
		
		while(true) {
			try {
				authView.startView();
				
				AlarmManager alarmManager = new AlarmManager(new AlarmService());
		        alarmManager.start();
		        
				homeView.homeView();
				
				alarmManager.stop();
			} catch (DatabaseException e) {
				System.out.println("データベースエラーが発生しました");
			}
			
			System.out.println("アプリケーションを終了しますか？(1.はい 2.いいえ)");
			int isFinish = Input.nextInt();
			if (isFinish == 1) {
				break;
			} else {
				continue;
			}
		}
		
		
		System.out.println("成功");
	}

}
