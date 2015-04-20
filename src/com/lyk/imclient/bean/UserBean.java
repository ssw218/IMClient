package com.lyk.imclient.bean;

public class UserBean extends BaseUserBean {
	private String mName;
	private String mIntroduce;
	private char mState;
	private String mImageURL;
	private String mImagePath;
	private String mFriends;
	private String mGroups;
	
	public UserBean() {

	}
	
	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getIntroduce() {
		return mIntroduce;
	}

	public void setIntroduce(String introduce) {
		this.mIntroduce = introduce;
	}

	public char getState() {
		return mState;
	}

	public void setState(char state) {
		this.mState = state;
	}

	public String getImageURL() {
		return mImageURL;
	}

	public void setImageURL(String imageURL) {
		this.mImageURL = imageURL;
	}

	public String getImagePath() {
		return mImagePath;
	}

	public void setImagePath(String imagePath) {
		this.mImagePath = imagePath;
	}

	public String getFriends() {
		return mFriends;
	}

	public void setFriends(String friends) {
		this.mFriends = friends;
	}

	public String getGroups() {
		return mGroups;
	}
	
	public void setGroups(String groups) {
		this.mGroups = groups;
	}
	
	@Override
	public String toString() {
		return "name : " + mName + 
				" introduce : " + mIntroduce +
				" state : " + mState + 
				" image url : " + mImageURL +
				" image path : " + mImagePath + 
				" friends : " + mFriends + 
				" groups : " + mGroups;
	}
	
}
