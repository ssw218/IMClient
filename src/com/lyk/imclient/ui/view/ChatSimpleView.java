package com.lyk.imclient.ui.view;

import com.lyk.imclient.R;
import com.lyk.imclient.activity.ChatActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatSimpleView extends FrameLayout {
	private static final String TAG = "ChatSimpleView";
	private static final boolean DEBUG = true;
	
	private Context mContext;
	private View mView;
	private ImageView mPoto;
	private TextView mName;
	private TextView mTime;
	private TextView mContent;
	private TextView mNumber;
	
	private OnClickListener openChatListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(mContext, ChatActivity.class);
			mContext.startActivity(intent);
		}
		
	};
	
	public ChatSimpleView(Context context) {
		super(context);
		mContext = context;
		mView = LayoutInflater.from(context).inflate(R.layout.tab_chats_view, null);
		mView.setOnClickListener(openChatListener);
		mPoto = (ImageView) mView.findViewById(R.id.imageview_tab_chats_view_photo);
		mName = (TextView) mView.findViewById(R.id.textview_tab_chats_view_chat_name);
		mTime = (TextView) mView.findViewById(R.id.textview_tab_chats_view_chat_time);
		mContent = (TextView) mView.findViewById(R.id.textview_tab_chats_view_chat_content);
		mNumber = (TextView) mView.findViewById(R.id.textview_tab_chats_view_chat_number);
//		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//		setBackgroundColor(0xFFFFFF);
		addView(mView);
	}
	
	private void init() {
		
	}
}
