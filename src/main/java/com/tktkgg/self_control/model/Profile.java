package com.tktkgg.self_control.model;

public class Profile {
	private int userId;
	private String bio;
	private String job;
	private int age;
	
	public Profile(int userId, String bio, String job, int age) {
		this.userId = userId;
		this.bio = bio;
		this.job = job;
		this.age = age;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public String getBio() {
		return this.bio;
	}
	
	public String getJob() {
		return this.job;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public void setJob(String job) {
		this.job = job;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
}
