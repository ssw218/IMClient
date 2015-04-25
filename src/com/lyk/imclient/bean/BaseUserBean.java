package com.lyk.imclient.bean;

public class BaseUserBean {
	private String mId;
	private String mName;
	
	public BaseUserBean() {
		
	}
	
	public String getId() {
		return mId;
	}

	public void setId(String id) {
		this.mId = id;
	}
	
	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}
}
