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
import com.lyk.imclient.util.SDCardManager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toolbar;

public class IMClientActivity extends FragmentActivity {
	private static final String TAG = "IMClientActivity";
	private static final boolean DEBUG = true;
	
	private Toolbar mToolbar;
	private SlidingTabLayout mSlidingTab;
	private ViewPager mViewPager;
	private ArrayList<Fragment> mFragments;
	private ViewPagerAdapter mViewPagerAdapter;
	
	private ContactsFragment mContactsFragment;
	private CirclesFragment mCirclesFragment;
	private ChatsFragment mChatsFragment;
	
	private UIHandler mUIHandler;
	
	private OnClickListener mNavigationListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
		
		}
		
	};

	private IBinder mServiceBinder;
	private Messenger mMessenger;
	
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			if (DEBUG) Log.e(TAG, "name : " + name.getClassName() + " name : " + ContactsService.class.getName());
			if (name.getClassName().equals(ContactsService.class.getName())) {
				if (DEBUG) Log.e(TAG, "onServiceConnected");
				Messenger messenger = new Messenger(service);
				if (DEBUG) Log.e(TAG, "messenger : " + messenger);
				Message message = new Message();
				message.what = ContactsService.ServiceHandler.MESSAGE_SEND_MESSENGER;
				message.replyTo = mMessenger;
				try {
					messenger.send(message);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

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
        mMessenger = new Messenger(mUIHandler);
        if (DEBUG) Log.e(TAG, "Messenger: " + mMessenger);
        
    }
    
    private void init() {
    	mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_im_head);
		setActionBar(mToolbar);

//		String nickName = "Li Yikun";
//		String introduce= "fall in love with Microsoft OneNote";

		getActionBar().setTitle("");
		mToolbar.setSubtitle("");
		mToolbar.setNavigationIcon(R.drawable.cat);
		
		mToolbar.setNavigationOnClickListener(mNavigationListener);

		mSlidingTab = (SlidingTabLayout) findViewById(R.id.slidingtablayout_activity_im_sliding_tabs);
		mViewPager = (ViewPager) findViewById(R.id.viewpager_activity_im_content);

		mChatsFragment =  new ChatsFragment();
		mContactsFragment = new ContactsFragment();
		mCirclesFragment = new CirclesFragment();
		
		mFragments = new ArrayList<Fragment>();
		mFragments.add(mChatsFragment);
		mFragments.add(mContactsFragment);
		mFragments.add(mCirclesFragment);

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
    
    private void updatePhoto (String id, String name) {
    	if (DEBUG) Log.e(TAG, "updatePhoto : " + id + " " + name);
    	SDCardManager sdcard = new SDCardManager();
    	Drawable drawable = sdcard.getImageFromSDCard(SDCardManager.FILE_PHOTO, name, 
    			mToolbar.getNavigationIcon().getBounds(), getResources());
    	mToolbar.setNavigationIcon(drawable);
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
    	private static final String TAG = "UIHandler";
    	private static final boolean DEBUG = false;
    	
		public static final int MESSAGE_CHATS_FRAGMENT_UPDATE = 1; 
		public static final int MESSAGE_CONTACTS_FRAGMENT_UPDATE = 2;
		public static final int MESSAGE_CIRCLES_FRAGMENT_UPDATE = 3;
		public static final int MESSAGE_CONTACTS_SERVICE_START = 4;
		public static final int MESSAGE_CHATS_SERVICE_START = 5;
		public static final int MESSAGE_USER_PHOTO_UPDATE = 6;
		public static final int MESSAGE_FRAGMENT_CONTACTS_UPDATE_INFO = 7;
		public static final int MESSAGE_FRAGMENT_CONTACTS_UPDATE_PHOTO = 8;
		
		public UIHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			if (DEBUG) Log.e(TAG, "UIHandler " + msg.what);
			switch (msg.what) {
				case MESSAGE_CHATS_FRAGMENT_UPDATE : break;
				case MESSAGE_CONTACTS_FRAGMENT_UPDATE : break;
				case MESSAGE_CIRCLES_FRAGMENT_UPDATE : break;
				case MESSAGE_CONTACTS_SERVICE_START : {
					Intent intent = new Intent(IMClientActivity.this, ContactsService.class);
					UserBean user = (UserBean) msg.obj;
			
					intent.putExtra(ContactsService.NAME_ID, user.getId());
					intent.putExtra(ContactsService.NAME_PHOTO, user.getImageURL());
					intent.putExtra(ContactsService.NAME_FRIENDS, user.getFriends());
					intent.putExtra(ContactsService.NAME_GROUPS, user.getGroups());
					if (DEBUG) Log.d(TAG, mToolbar.getNavigationIcon().getBounds().toShortString());
					intent.putExtra(ContactsService.NAME_DRAWABLE, mToolbar.getNavigationIcon().getBounds());
					
					bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
//					startService(intent);
					
					setUIName(user.getName());
					setUIIntroduce(user.getIntroduce());
					
					Message message = new Message();
					message.what = MESSAGE_CHATS_SERVICE_START;
					sendMessage(message);
					
					for (String next: user.getFriends().split(",")) {
						Message message2 = new Message();
						message2.what = ContactsFragment.FragmentHandler.MESSAGE_ADD_FRIEND;
						message2.obj = next;
						mContactsFragment.getHandler().sendMessage(message2);
					}
					break; }
				case MESSAGE_CHATS_SERVICE_START : {
					Intent intent = new Intent(IMClientActivity.this, ChatsService.class);
					startService(intent);
					break; }
				case MESSAGE_USER_PHOTO_UPDATE : {
					// id and photo name
					String[] m = (String[]) msg.obj;
					updatePhoto(m[0], m[1]);
					break; }
				case MESSAGE_FRAGMENT_CONTACTS_UPDATE_INFO : {
					Message message = Message.obtain(msg);
					message.what = ContactsFragment.FragmentHandler.MESSAGE_UPDATE_FRIEND;
					mContactsFragment.getHandler().sendMessage(message);
					break; }
				case MESSAGE_FRAGMENT_CONTACTS_UPDATE_PHOTO : {
					Message message = Message.obtain(msg);
					message.what = ContactsFragment.FragmentHandler.MESSAGE_UPDATE_PHOTO;
					mContactsFragment.getHandler().sendMessage(message);
					break; }
			}
		}
		
	};
    
}