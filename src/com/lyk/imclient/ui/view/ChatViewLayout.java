package com.lyk.imclient.ui.view;

import com.lyk.imclient.R;

import android.content.Context;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatViewLayout extends LinearLayout {

	private Context mContext;
	
	public ChatViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}
	
	public void sendMessage(String text) {
		TextView time = new TimeTextView(mContext, new Time("GMT+8"));
		addView(time);
		View sendView = LayoutInflater.from(mContext).inflate(R.layout.chat_send_bubble_view, null);
		TextView sendText = (TextView) sendView.findViewById(R.id.text_chat_send_bubble_view_content);
		sendText.setText(text);
		addView(sendView);
	}
	
	class TimeTextView extends TextView {

		public TimeTextView(Context context, Time time) {
			super(context);
			LayoutParams l = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			l.bottomMargin = 5;
			l.topMargin = 10;
			l.gravity = Gravity.CENTER;
			setLayoutParams(l);
			
			setText(getTimeString(time));
		}
		
		private String getTimeString(Time time) {
			if (time.hour < 12) 
				return String.format("%d年%月%日，上午%d：%d", 
						time.year, time.month, time.monthDay, time.hour, time.minute);
			else
				return String.format("%d年%月%日，下午%d：%d", 
						time.year, time.month, time.monthDay, time.hour, time.minute);
			
		}
		
	}

}
