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
		// TODO Auto-generated method stub
		return mFragments.get(pos);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFragments.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return "Tab_1" + position;
	}
	
}
