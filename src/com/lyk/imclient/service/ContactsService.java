package com.lyk.imclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

// Update contacts info, just like image, name, introduce, is online

public class ContactsService extends Service {
	private static final String TAG = "ContanctsService";
	private static final boolean DEBUG = true;
	
	public static final String NAME_PHOTO = "photo";
	public static final String NAME_FRIENDS = "friends";
	public static final String NAME_GROUPS = "groups";
	
	private String mImageURL;
	private String[] mFriends;
	private String[] mGroups;

	@Override
	public void onCreate() {
		super.onCreate();
		if (DEBUG) Log.v(TAG, "onCreate");
		// download host user photo and update UI;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (DEBUG) Log.v(TAG, "onStartCommand");
		mImageURL = intent.getStringExtra(NAME_PHOTO);
		mFriends = intent.getStringExtra(NAME_FRIENDS).split(",");
		mGroups = intent.getStringExtra(NAME_GROUPS).split(",");
		if (DEBUG) Log.e(TAG, "image url : " + mImageURL + " friends : " + mFriends +
				" groups : " + mGroups);
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
	
	class ImageDownloadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled(String result) {
			super.onCancelled(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
		
	}

}
