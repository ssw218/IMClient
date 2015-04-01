package com.lyk.imclient.ui.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> mFragments;
	
	public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
		super(fm);
		mFragments = fragments;
	}

	@Override
	public Fragment getItem(int pos) {
		return mFragments.get(pos);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}
	
	private static final int POSITION_CHATS = 0;
	private static final int POSITION_CONTACTS = 1;
	private static final int POSITION_CIRCLES = 2;
	
	private static final String TITLE_CHATS = "chats";
	private static final String TITLE_CONTACTS = "contacts";
	private static final String TITLE_CIRCLES = "circles";
	
	@Override
	public CharSequence getPageTitle(int position) {
		if (position == POSITION_CHATS)
			return TITLE_CHATS;
		else if (position == POSITION_CONTACTS)
			return TITLE_CONTACTS;
		else if (position == POSITION_CIRCLES)
			return TITLE_CIRCLES;
		else
			return null;
	}
	
}
