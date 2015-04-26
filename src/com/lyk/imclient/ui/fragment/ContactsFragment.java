package com.lyk.imclient.ui.fragment;

import java.util.ArrayList;

import com.lyk.imclient.R;
import com.lyk.imclient.bean.FriendUserBean;
import com.lyk.imclient.util.IPManager;
import com.lyk.imclient.util.SDCardManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ContactsFragment extends Fragment {
	private static final String TAG = "ContactsFragment";
	private static final boolean DEBUG = true;

	private LinearLayout mFriendsLayout;
	private LinearLayout mGroupsLayout;

	private ArrayList<ContactView> mFriendViews;

	private FragmentHandler mHandler;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab_contacts, container,
				false);
		ScrollView scrollView = new ScrollView(getActivity());
		ScrollView.LayoutParams p = new ScrollView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		scrollView.setVerticalScrollBarEnabled(true);
		scrollView.setBackgroundColor(0xFFFFFFFF);
		
		scrollView.addView(view, p);
		mFriendsLayout = (LinearLayout) view
				.findViewById(R.id.fragment_tab_contacts_friends_layout);
		mGroupsLayout = (LinearLayout) view
				.findViewById(R.id.fragment_tab_contacts_groups_layout);
		mFriendViews = new ArrayList<ContactView>();
		mHandler = new FragmentHandler();
		return scrollView;
	}
	
	public Handler getHandler() {
		return mHandler;
	}

	public class FragmentHandler extends Handler {
		private static final String TAG = "FragmentHandler";
		private static final boolean DEBUG = true;
		
		public static final int MESSAGE_ADD_FRIEND = 1;
		public static final int MESSAGE_DELETE_FRIEND = 2;
		public static final int MESSAGE_UPDATE_FRIEND = 3;
		public static final int MESSAGE_UPDATE_PHOTO = 4;

		@Override
		public void handleMessage(Message msg) {
			if (DEBUG) Log.e(TAG, "Message : " + msg.what);
			switch (msg.what) {
			case MESSAGE_ADD_FRIEND: {
				String id = (String) msg.obj;
				boolean add = true;
				for (ContactView next : mFriendViews) {
					if (next.getFriendId().equals(id)) {
						add = false;
					}
				}
				if (add) {
					ContactView contactView = new ContactView(getActivity(), id);
					mFriendViews.add(contactView);
					mFriendsLayout.addView(contactView);
				}
				break; }
			case MESSAGE_DELETE_FRIEND:
				break;
			case MESSAGE_UPDATE_FRIEND: {
				FriendUserBean friend = (FriendUserBean) msg.obj;
				boolean add = true;
				for (ContactView next : mFriendViews) {
					if (next.getFriendId().equals(friend.getId())) {
						add = false;
						next.setName(friend.getName());
						next.setIntroduce(friend.getIntroduce());
					}
				}
				if (add) {
					ContactView contactView = new ContactView(getActivity(),
							friend.getId());
					contactView.setName(friend.getName());
					contactView.setIntroduce(friend.getIntroduce());
					mFriendViews.add(contactView);
					mFriendsLayout.addView(contactView);
				}
				break; } 
			case MESSAGE_UPDATE_PHOTO : {
				String id = ((FriendUserBean) msg.obj).getId();
				String name = ((FriendUserBean) msg.obj).getImageURL();
				SDCardManager sdcard = new SDCardManager(id);
				for (ContactView next : mFriendViews) {
					if (next.getFriendId().equals(id)) {
						next.setImageSrc(sdcard.getBitmap(SDCardManager.FILE_PHOTO, name));
					}
				}
				break; }
			}
		}

	}

	class ContactView extends RelativeLayout {
		private String mId;
		private View mView;
		private ImageView mPhoto;
		private TextView mName;
		private TextView mIntroduce;

		public ContactView(Context context, String id) {
			super(context);
			mId = id;
			mView = LayoutInflater.from(context).inflate(
					R.layout.contact_people_view, null);
			mPhoto = (ImageView) mView.findViewById(R.id.contact_people_photo);
			mName = (TextView) mView
					.findViewById(R.id.contact_people_textview_name);
			mIntroduce = (TextView) mView
					.findViewById(R.id.contact_people_textview_introduce);
			LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			p.topMargin = 10;
			p.bottomMargin = 10;
			addView(mView, p);
		}

		public String getFriendId() {
			return mId;
		}

		public void setImageSrc(Bitmap bitmap) {
			mPhoto.setImageBitmap(bitmap);
		}

		public void setName(String name) {
			mName.setText(name);
		}

		public void setIntroduce(String introduce) {
			mIntroduce.setText(introduce);
		}

		public void setOnline(String state) {

		}

	}

}