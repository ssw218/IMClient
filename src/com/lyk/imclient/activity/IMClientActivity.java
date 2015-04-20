package com.lyk.imclient.activity;

import java.util.ArrayList;

import com.lyk.imclient.R;
import com.lyk.imclient.bean.UserBean;
import com.lyk.imclient.service.ChatsService;
import com.lyk.imclient.service.ContactsService;
import com.lyk.imclient.ui.adapter.ViewPagerAdapter;
import com.lyk.imclient.ui.fragment.ChatsFragment;
import com.lyk.imclient.ui.fragment.CirclesFragment;
import com.lyk.imclient.ui.fragment.ContactsFragment;
import com.lyk.imclient.ui.fragment.LoginFragment;
import com.lyk.imclient.ui.tab.SlidingTabLayout;
import com.lyk.imclient.util.IPManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
	
	private UIHandler mUIHandler;
	
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
        
        init();
    }
    
    private void init() {
    	mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setActionBar(mToolbar);

//		String nickName = "Li Yikun";
//		String introduce= "fall in love with Microsoft OneNote";

		getActionBar().setTitle("");
		mToolbar.setSubtitle("");
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
		
		mUIHandler = new UIHandler(getMainLooper());
    }
    
    private void setUIName (String nickName) {
    	getActionBar().setTitle(nickName);;
    }
    
    private void setUIIntroduce (String introduce) {
    	mToolbar.setSubtitle(introduce);
    }
    
    //Called by LoginFragment
    public void hideLoginFragment(Fragment fragment) {
    	FragmentTransaction fTAnimation = getSupportFragmentManager().beginTransaction();
    	fTAnimation.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
    	fTAnimation.commit();
    	FragmentTransaction fTLogin = getSupportFragmentManager().beginTransaction();
    	fTLogin.hide(fragment);
    	fTLogin.commit();
    }
    
    public UIHandler getUIHandler() {
    	return mUIHandler;
    }
    
    public class UIHandler extends Handler {
		public static final int MESSAGE_CHATS_FRAGMENT_UPDATE = 1; 
		public static final int MESSAGE_CONTACTS_FRAGMENT_UPDATE = 2;
		public static final int MESSAGE_CIRCLES_FRAGMENT_UPDATE = 3;
		public static final int MESSAGE_CONTACTS_SERVICE_START = 4;
		public static final int MESSAGE_CHATS_SERVICE_START = 5;
		
		public UIHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MESSAGE_CHATS_FRAGMENT_UPDATE : break;
				case MESSAGE_CONTACTS_FRAGMENT_UPDATE : break;
				case MESSAGE_CIRCLES_FRAGMENT_UPDATE : break;
				case MESSAGE_CONTACTS_SERVICE_START : {
					Intent intent = new Intent(IMClientActivity.this, ContactsService.class);
					UserBean user = (UserBean) msg.obj;
			
					intent.putExtra(ContactsService.NAME_PHOTO, user.getImageURL());
					intent.putExtra(ContactsService.NAME_FRIENDS, user.getFriends());
					intent.putExtra(ContactsService.NAME_GROUPS, user.getGroups());
					startService(intent);
					
					setUIName(user.getName());
					setUIIntroduce(user.getIntroduce());
					
					Message message = new Message();
					message.what = MESSAGE_CHATS_SERVICE_START;
					sendMessage(message);
					break; }
				case MESSAGE_CHATS_SERVICE_START : {
					Intent intent = new Intent(IMClientActivity.this, ChatsService.class);
					startService(intent);
					break; }
			}
		}
		
	};
    
}