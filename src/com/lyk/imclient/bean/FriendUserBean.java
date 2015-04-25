package com.lyk.imclient.bean;

public class FriendUserBean extends BaseUserBean {
	private String mIntroduce;
	private char mState;
	private String mImageURL;
	private String mImagePath;
	
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
}
