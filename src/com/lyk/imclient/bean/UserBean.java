package com.lyk.imclient.bean;

public class UserBean {
	private String mUserName;
	private String mPassword;
	private String mIp;
	
	public UserBean() {

	}
	
	public UserBean(String userName, String password, String ip) {
		mUserName = userName;
		mPassword = password;
		mIp = ip;
	}

	public String getUser() {
		return mUserName;
	}

	public void setUser(String userName) {
		this.mUserName = userName;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		this.mPassword = password;
	}

	public String getIp() {
		return mIp;
	}

	public void setIp(String ip) {
		this.mIp = ip;
	}
	
	
}
