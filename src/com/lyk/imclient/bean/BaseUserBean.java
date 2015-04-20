package com.lyk.imclient.bean;

public class BaseUserBean {
	private String mId;
	private String mPassword;
	private String mIp;
	
	public BaseUserBean() {

	}
	
	public BaseUserBean(String id, String password, String ip) {
		mId = id;
		mPassword = password;
		mIp = ip;
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		this.mId = id;
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
	
	@Override
	public String toString() {
		return "id : " + mId + " password : " + mPassword + " ip : " + mIp;
	}
	
}
