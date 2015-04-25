package com.lyk.imclient.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.lyk.imclient.bean.FriendUserBean;
import com.lyk.imclient.bean.UserBean;
import com.lyk.imclient.factory.UserFactory;

import android.util.Log;
import android.util.Xml;

public class UserXmlParser {
	private static final String TAG = "UserXmlParser";
	private static final boolean DEBUG = false;

	private static final String TAG_NAME = "name";
	private static final String TAG_PHOTO = "photo";
	private static final String TAG_FRIENDS = "friends";
	private static final String TAG_GROUPS = "groups";
	private static final String TAG_INTRODUCE = "introduce";

	public static UserBean getUser(String xml) {
		UserBean user = UserFactory.createUser();
		XmlPullParserFactory xmlFactory = null;
		XmlPullParser parser = null;
		StringReader reader = new StringReader(xml);
		try {
			xmlFactory = XmlPullParserFactory.newInstance();
			parser = xmlFactory.newPullParser();
			parser.setInput(reader);
			if (DEBUG)
				Log.e(TAG, "paser text : " + parser.getText());
			int eventType = parser.getEventType();
			String name = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (DEBUG)
					Log.e(TAG, "eventType : " + eventType + " parser text : " + 
							parser.getText() + " name : " + parser.getName());
				if (eventType == XmlPullParser.START_TAG) {
					name = parser.getName();
				} else if (eventType == XmlPullParser.TEXT) {
					if (name.equals(TAG_NAME)) {
						user.setName(parser.getText());
					} else if (name.equals(TAG_PHOTO)) {
						user.setImageURL(parser.getText());
					} else if (name.equals(TAG_FRIENDS)) {
						user.setFriends(parser.getText());
					} else if (name.equals(TAG_GROUPS)) {
						user.setGroups(parser.getText());
					} else if (name.equals(TAG_INTRODUCE)) {
						user.setIntroduce(parser.getText());
					}
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return user;
	}
	
	public static FriendUserBean getFriend(String xml) {
		FriendUserBean friend = UserFactory.createFriend();
		XmlPullParserFactory xmlFactory = null;
		XmlPullParser parser = null;
		StringReader reader = new StringReader(xml);
		try {
			xmlFactory = XmlPullParserFactory.newInstance();
			parser = xmlFactory.newPullParser();
			parser.setInput(reader);
			int eventType = parser.getEventType();
			String name = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					name = parser.getName();
				} else if (eventType == XmlPullParser.TEXT) {
					if (name.equals(TAG_NAME)) {
						friend.setName(parser.getText());
					} else if (name.equals(TAG_PHOTO)) {
						friend.setImageURL(parser.getText());
					} else if (name.equals(TAG_INTRODUCE)) {
						friend.setIntroduce(parser.getText());
					}
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return friend;
	}
}
