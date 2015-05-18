package com.lyk.imclient.bean;

public class HostUserBean extends BaseUserBean {
	private String mIp;
	private String mPhone;
	private String mPassword;
	
	public HostUserBean() {
		
	}
	
	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String phone) {
		this.mPhone = phone;
	}

	public String getIp() {
		return mIp;
	}

	public void setIp(String ip) {
		this.mIp = ip;
	}
	
	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		this.mPassword = password;
	}
	
	@Override
	public String toString() {
		return "id : " + getId() + " password : " + mPassword + " phone : " + mPhone + " ip : " + mIp;
	}
	
}
