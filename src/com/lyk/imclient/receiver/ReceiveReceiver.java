package com.lyk.imclient.receiver;

import com.lyk.imclient.bean.MessageBean;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiveReceiver extends BroadcastReceiver {
	private static final String TAG = "ReceiveReceiver";
	private static final boolean DEBUG = true;
	
	public static final String ACTION = "com.lyk.imclient.action.RECEIVE_MESSAGE";
	
	private OnMessageReceiveListener mListener;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			byte[] data = intent.getByteArrayExtra("data");
			mListener.onMessageReceive(data);
		}
	}
	
	public void setOnMessageReceiveListener(OnMessageReceiveListener listener) {
		mListener = listener;
	}
	
	public interface OnMessageReceiveListener {
		public void onMessageReceive(byte[] data);
	}
	
}