package com.lyk.imclient.bean;

public class HostUserBean extends BaseUserBean {

	private String mPassword;
	private String mIp;
	
	public HostUserBean() {

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
