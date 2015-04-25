package com.lyk.imclient.bean;

public class UserBean extends FriendUserBean {
	private String mFriends;
	private String mGroups;
	
	public UserBean() {

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
		return "name : " + getName() + 
				" introduce : " + getIntroduce() +
				" state : " + getState() + 
				" image url : " + getImageURL() +
				" image path : " + etmImagePath() + 
				" friends : " + mFriends + 
				" groups : " + mGroups;
	}
	
}
