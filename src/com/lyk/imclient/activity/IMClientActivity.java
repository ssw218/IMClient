package com.lyk.imclient.activity;

import java.util.ArrayList;

import com.lyk.imclient.R;
import com.lyk.imclient.ui.adapter.ViewPagerAdapter;
import com.lyk.imclient.ui.fragment.ChatsFragment;
import com.lyk.imclient.ui.fragment.CirclesFragment;
import com.lyk.imclient.ui.fragment.ContactsFragment;
import com.lyk.imclient.ui.fragment.LoginFragment;
import com.lyk.imclient.ui.tab.SlidingTabLayout;
import com.lyk.imclient.util.IPManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toolbar;

public class IMClientActivity extends FragmentActivity {
	
	private Toolbar mToolbar;

	private SlidingTabLayout mSlidingTab;

	private ViewPager mViewPager;

	private ArrayList<Fragment> mFragments;

	private ViewPagerAdapter mViewPagerAdapter;
	
	private OnClickListener mNavigationListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			new IPManager().getNetworkIP();
			
		}
		
	};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Login
        if (true) {
        	FragmentTransaction fTLogin = fragmentManager.beginTransaction();
        	fTLogin.replace(android.R.id.content, LoginFragment.getInstance("", ""));
        	fTLogin.commit();
        }
        
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setActionBar(mToolbar);

		String nickName = "Li Yikun";
		String introduce= "fall in love with Microsoft OneNote";

		getActionBar().setTitle(nickName);
		mToolbar.setSubtitle(introduce);
		mToolbar.setNavigationIcon(R.drawable.cat);
		mToolbar.setNavigationOnClickListener(mNavigationListener);

		mSlidingTab = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		mFragments = new ArrayList<Fragment>();
		mFragments.add(new ChatsFragment());
		mFragments.add(new ContactsFragment());
		mFragments.add(new CirclesFragment());

		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
		mViewPager.setOffscreenPageLimit(mFragments.size());
		mViewPager.setAdapter(mViewPagerAdapter);

		mSlidingTab.setViewPager(mViewPager);
    }
}