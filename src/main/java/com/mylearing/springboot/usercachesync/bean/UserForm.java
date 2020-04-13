package com.mylearing.springboot.usercachesync.bean;

import java.io.Serializable;

public class UserForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String role;
	
	public UserForm() {}
	
	public UserForm(String userName, String role) {
		super();
		this.userName = userName;
		this.role = role;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
