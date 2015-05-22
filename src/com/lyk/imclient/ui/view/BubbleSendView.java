package com.lyk.imclient.ui.view;

import com.lyk.imclient.R;
import com.lyk.imclient.activity.ChatActivity;
import com.lyk.imclient.util.PlayManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BubbleSendView extends RelativeLayout {
	private PlayManager mPlayManager;
	private ImageView mHostPhoto = null;
	private TextView mSendText = null;
	private TextView mExtraText = null;
	private String mPath = null;
	private int mType;
	
	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mType == ChatActivity.MessageType.MESSAGE_MEDIA) {
				mPlayManager.prepare(mPath);
				mPlayManager.start();
			}
		}
		
	};
	
	public BubbleSendView(Context context) {
		super(context);
		mPlayManager = new PlayManager();
		View view = LayoutInflater.from(context).inflate(R.layout.chat_send_view, null);
		mHostPhoto = (ImageView) view.findViewById(R.id.imageview_chat_send_view_photo);
		mSendText = (TextView) view.findViewById(R.id.textview_chat_send_bubble_view_content);
		mSendText.setOnClickListener(mOnClickListener);
		mExtraText = (TextView) view.findViewById(R.id.textview_chat_send_bubble_view_time);
		addView(view);
	}
	
	public void setHostPhoto(Bitmap bitmap) {
		mHostPhoto.setImageBitmap(bitmap);
	}
	
	public void setSendMessage(String text) {
		mSendText.setText(text);
	}
	
	public void setRecordTime(String time) {
		if (time == null) {
			mExtraText.setText("");
		} else {
			mPlayManager.prepare(mPath);
			mExtraText.setText(mPlayManager.getMediaSecond() + "''");
		}
	}

	public void setRecordPath(String path) {
		mPath = path;
	}

	public void setType(int i) {
		mType = i;
		
	}
}
