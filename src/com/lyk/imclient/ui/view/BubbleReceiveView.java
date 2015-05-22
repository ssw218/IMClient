package com.lyk.imclient.ui.view;

import com.lyk.imclient.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BubbleReceiveView extends RelativeLayout {
	private ImageView mFriendPhoto = null;
	private TextView mReceiveText = null;
	private TextView mExtraText = null;
	
	public BubbleReceiveView(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.chat_receive_view, null);
		mFriendPhoto = (ImageView) view.findViewById(R.id.imageview_chat_receive_view_photo);
		mReceiveText = (TextView) view.findViewById(R.id.textview_chat_receive_bubble_view_content);
		mExtraText = (TextView) view.findViewById(R.id.textview_chat_receive_bubble_view_time);
		addView(view);
	}
	
	public void setFriendPhoto(Bitmap bitmap) {
		mFriendPhoto.setImageBitmap(bitmap);
	}
	
	public void setReceiveMessage(String text) {
		mReceiveText.setText(text);
	}
	
	public void setRecordTime(String time) {
		if (time == null)
			time = "";
		mExtraText.setText(time);
	}

}
