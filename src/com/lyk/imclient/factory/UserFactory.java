package com.lyk.imclient.factory;

import com.lyk.imclient.bean.FriendUserBean;
import com.lyk.imclient.bean.HostUserBean;
import com.lyk.imclient.bean.UserBean;

public class UserFactory {
	
	public static FriendUserBean createFriend() {
		return new FriendUserBean();
	} 
	
	public static HostUserBean createHostUser() {
		return new HostUserBean();
	}
	
	public static UserBean createUser() {
		return new UserBean();
	}
	
}
