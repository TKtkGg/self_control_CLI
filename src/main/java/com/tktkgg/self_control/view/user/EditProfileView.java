package com.tktkgg.self_control.view.user;

import com.tktkgg.self_control.model.Profile;
import com.tktkgg.self_control.service.ProfileService;
import com.tktkgg.self_control.util.Input;
import com.tktkgg.self_control.util.SessionManager;
import com.tktkgg.self_control.view.MenuAction;
import com.tktkgg.self_control.view.ViewUtils;

public class EditProfileView implements MenuAction {
	private final ProfileService ps = new ProfileService();
	
	@Override
	public void execute() {
		System.out.println("プロフィール編集画面です。");
		
		Profile profile = ps.getProfile(SessionManager.getUser().getId());
		System.out.println("自己紹介：" + profile.getBio());
		System.out.println("(入力)→");
		String bio = Input.nextLine();
		
		System.out.println("職業：" + profile.getJob());
		System.out.println("(入力)→");
		String job = Input.nextLine();
		
		System.out.println("年齢：" + profile.getAge());
		System.out.println("(入力)→");
		int age = Input.nextInt();
		
		System.out.println("これでよろしいですか？（1:はい 2:いいえ）");
		if (ViewUtils.confirm("編集")) {
			profile.setBio(bio);
			profile.setJob(job);
			profile.setAge(age);
			ps.updateProfile(profile);
		}
	}

}
