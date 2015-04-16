package com.lyk.imclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MessageService extends Service {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
