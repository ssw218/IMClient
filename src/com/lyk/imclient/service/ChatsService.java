package com.lyk.imclient.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;

public class ChatsService extends Service {
	
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
 
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
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
