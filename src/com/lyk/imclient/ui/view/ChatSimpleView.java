package com.lyk.imclient.ui.view;

import com.lyk.imclient.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatSimpleView extends FrameLayout {
	private static final String TAG = "ChatSimpleView";
	private static final boolean DEBUG = true;
	
	private View mView;
	private ImageView mPoto;
	private TextView mName;
	private TextView mTime;
	private TextView mContent;
	private TextView mNumber;
	
	public ChatSimpleView(Context context) {
		super(context);
		mView = LayoutInflater.from(context).inflate(R.layout.tab_chats_view, null);
		mPoto = (ImageView) mView.findViewById(R.id.image_photo);
		mName = (TextView) mView.findViewById(R.id.text_chat_name);
		mTime = (TextView) mView.findViewById(R.id.text_chat_time);
		mContent = (TextView) mView.findViewById(R.id.text_chat_content);
		mNumber = (TextView) mView.findViewById(R.id.text_chat_number);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		setBackgroundColor(0xFFFFFF);
		addView(mView, layoutParams);
	}
	
	
}
