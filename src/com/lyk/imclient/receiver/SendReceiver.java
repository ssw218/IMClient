package com.lyk.imclient.receiver;

import com.lyk.imclient.bean.MessageBean;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SendReceiver extends BroadcastReceiver {
	private static final String TAG = "SendReceiver";
	private static final boolean DEBUG = true;
	
	public static final String ACTION = "com.lyk.imclient.action.SEND_MESSAGE";
	
	private OnMessageSendListener mListener;
	
//	private MessageBean mMessageBean;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			byte[] data = intent.getByteArrayExtra("data");
//			mMessageBean = MessageBean.createMessage(data);
			mListener.onMessageSend(data);
		}
	}
	
	public void setOnMessageSendListener(OnMessageSendListener listener) {
		mListener = listener;
	}
	
	public interface OnMessageSendListener {
		public void onMessageSend(byte[] data);
	}
	
}
