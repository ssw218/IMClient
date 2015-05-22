package com.lyk.imclient.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpClientConnection;

import com.lyk.imclient.activity.IMClientActivity;
import com.lyk.imclient.bean.FriendUserBean;
import com.lyk.imclient.factory.UserFactory;
import com.lyk.imclient.util.SDCardManager;
import com.lyk.imclient.util.ServerManager;
import com.lyk.imclient.util.UserXmlParser;

import android.app.Service;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

// Update contacts info, just like image, name, introduce, is online

public class ContactsService extends Service {
	private static final String TAG = "ContanctsService";
	private static final boolean DEBUG = false;

	public static final String NAME_PHOTO = "photo";
	public static final String NAME_FRIENDS = "friends";
	public static final String NAME_GROUPS = "groups";
	public static final String NAME_ID = "id";
	public static final String NAME_DRAWABLE = "drawable";

	private String mId;
	private String mImageURL;
	private String[] mFriends;
	private String[] mGroups;

	private Messenger mMessengerReceiver;
	private Messenger mMessengerSend;
	private ServiceHandler mHandler;
	
	private ConcurrentHashMap<String, AsyncTask> mFriendsUpdateTaskMap;
	private ConcurrentHashMap<String, AsyncTask> mImageDownloadTaskMap;

	@Override
	public void onCreate() {
		super.onCreate();
		if (DEBUG)
			Log.v(TAG, "onCreate");
		mHandler = new ServiceHandler();
		mMessengerReceiver = new Messenger(mHandler);
		
		mFriendsUpdateTaskMap = new ConcurrentHashMap<String, AsyncTask>();
		mImageDownloadTaskMap = new ConcurrentHashMap<String, AsyncTask>();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (DEBUG)
			Log.v(TAG, "onStartCommand");
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
		if (DEBUG)
			Log.v(TAG, "onBind");
		mId = intent.getStringExtra(NAME_ID);
		mImageURL = intent.getStringExtra(NAME_PHOTO);
		mFriends = intent.getStringExtra(NAME_FRIENDS).split(",");
		mGroups = intent.getStringExtra(NAME_GROUPS).split(",");
		
		if (DEBUG)
			Log.e(TAG, "image url : " + mImageURL + " friends : " + mFriends
					+ " groups : " + mGroups);

		// find image
		SDCardManager sdcard = new SDCardManager();
		if (!sdcard.exists(SDCardManager.FILE_PHOTO, mImageURL)) {
//			ImageDownloadTask imageTask = new ImageDownloadTask();
//			imageTask.execute(mId, mImageURL);
			Message message = new Message();
			message.what = ServiceHandler.MESSAGE_UPDATE_PHOTO;
			String[] params = new String[] {mId, mImageURL};
			message.obj = params;
			mHandler.sendMessage(message);
		} else {
			if (DEBUG)
				Log.e(TAG, "Photo is here");
			// send message to activity to update UI
			new Thread() {
				public void run() {
					while (mMessengerSend == null);
					Message message = new Message();
					message.what = ServiceHandler.MESSAGE_UPDATE_IMAGE_SEND_DELAY;
					String[] params = new String[] { mId, mImageURL };
					message.obj = params;
					mHandler.sendMessage(message);
				}
			}.start();
		}
		
		// request friends info
		for (String next : mFriends) {
			Message message = new Message();
			message.what = ServiceHandler.MESSAGE_UPDATE_FIRNED_INFO;
			message.obj = next;
			mHandler.sendMessage(message);
		}
		
		// download photo
		
		return mMessengerReceiver.getBinder();
	}

	public class ServiceHandler extends Handler {
		public static final int MESSAGE_SEND_MESSENGER = 1;
		public static final int MESSAGE_UPDATE_IMAGE_SEND_DELAY = 2;
		public static final int MESSAGE_UPDATE_FIRNED_INFO = 3;
		public static final int MESSAGE_UPDATE_PHOTO = 4;
		public static final int MESSAGE_UPDATT_SQL_USER = 5;
		public static final int MESSAGE_UPDATT_SQL_FRIENDS = 6;

		@Override
		public void handleMessage(Message msg) {
			if (DEBUG)
				Log.e(TAG, "ServiceHandler " + msg.what);
			switch (msg.what) {
			case MESSAGE_SEND_MESSENGER: {
				mMessengerSend = msg.replyTo;
				if (DEBUG)
					Log.e(TAG, "Messenger " + mMessengerSend);
				break; }
			case MESSAGE_UPDATE_IMAGE_SEND_DELAY: {
				Message message = Message.obtain(msg);
				message.what = IMClientActivity.UIHandler.MESSAGE_USER_PHOTO_UPDATE;
				try {
					mMessengerSend.send(message);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break; }
			case MESSAGE_UPDATE_FIRNED_INFO: {
				String id = (String) msg.obj;
				if (mFriendsUpdateTaskMap.containsKey(id)) {
					if (mFriendsUpdateTaskMap.get(id).getStatus().equals(Status.FINISHED)) {
						mFriendsUpdateTaskMap.remove(id);
						FriendInfoUpdateTask task = new FriendInfoUpdateTask();
						task.execute(id);
						mFriendsUpdateTaskMap.put(id, task);
					}
				} else {
					FriendInfoUpdateTask task = new FriendInfoUpdateTask();
					task.execute(id);
					mFriendsUpdateTaskMap.put(id, task);
				}
				break; }
			case MESSAGE_UPDATE_PHOTO : {
				String id = ((String[]) msg.obj)[0];
				String imageURL = ((String[]) msg.obj)[1];
				if (mImageDownloadTaskMap.containsKey(imageURL)) {
					if (mImageDownloadTaskMap.get(imageURL).getStatus().equals(Status.FINISHED)) {
						mImageDownloadTaskMap.remove(imageURL);
						ImageDownloadTask task = new ImageDownloadTask();
						task.execute(id, imageURL);
						mImageDownloadTaskMap.put(imageURL, task);
					}
				} else {
					ImageDownloadTask task = new ImageDownloadTask();
					task.execute(id, imageURL);
					mImageDownloadTaskMap.put(imageURL, task);
				}
				break; }
			case MESSAGE_UPDATT_SQL_USER:
				break;
			case MESSAGE_UPDATT_SQL_FRIENDS:
				break;
			}
		}

	}

	class FriendInfoUpdateTask extends AsyncTask<String, Void, FriendUserBean> {
		private static final String TAG = "FriendInfoUpdateTask";
		private static final boolean DEBUG = false;
		private static final int TIME_OUT_MILLIS = 20000;
		
		@Override
		protected FriendUserBean doInBackground(String... params) {
			String id = params[0];
			FriendUserBean friend = null;
			String string = new String("http://"
					+ ServerManager.SERVER_IP + ":" + ServerManager.HTTP_PORT + "/" + ServerManager.SERVER_NAME
					+ "/" + ServerManager.SERVLET_FRIEND_INFO + "?id=" + id);
			URL url = null;
			HttpURLConnection conn = null;
			InputStream inputStream = null;
			BufferedReader reader = null;
			try {
				url = new URL(string);
				conn = (HttpURLConnection) url.openConnection();
				inputStream = conn.getInputStream();
				reader = new BufferedReader(new InputStreamReader(inputStream));
				String temp = null;
				StringBuffer xml = new StringBuffer();
				while ((temp = reader.readLine()) != null) {
					xml.append(temp);
				}
				friend = UserXmlParser.getUser(xml.toString());
				friend.setId(id);
				friend.setImagePath(friend.getImageURL());
				if (DEBUG) Log.v(TAG, "friend info : " + friend);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return friend;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(FriendUserBean result) {
			// doing some SQL
			Message message1 = new Message();
			message1.what = ServiceHandler.MESSAGE_UPDATT_SQL_FRIENDS;
			message1.obj = result;
			mHandler.sendMessage(message1);
			
			// download image
			SDCardManager sdcard = new SDCardManager();
			if (!sdcard.exists(SDCardManager.FILE_PHOTO, result.getImagePath())) {
				Message message2 = new Message();
				message2.what = ServiceHandler.MESSAGE_UPDATE_PHOTO;
				String[] params = new String[]{result.getId(), result.getImageURL()};
				message2.obj = params;
				mHandler.sendMessage(message2);
			} else {
				Message message2 = new Message();
				message2.what = IMClientActivity.UIHandler.MESSAGE_FRAGMENT_CONTACTS_UPDATE_PHOTO;
				message2.obj = result;
				try {
					mMessengerSend.send(message2);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			
			// update info
			Message message3 = new Message();
			message3.what = IMClientActivity.UIHandler.MESSAGE_FRAGMENT_CONTACTS_UPDATE_INFO;
			message3.obj = result;
			try {
				mMessengerSend.send(message3);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void onCancelled(FriendUserBean result) {
			super.onCancelled(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
		
	}
	
	class ImageDownloadTask extends AsyncTask<String, Void, String[]> {
		private static final String TAG = "ImageDownloadTask";
		private static final boolean DEBUG = false;
		private static final int TIME_OUT_MILLIS = 20000;

		@Override
		protected String[] doInBackground(String... params) {
			if (DEBUG)
				Log.e(TAG, "doInBackground");
			String id = params[0];
			String name = params[1];
			StringBuilder stringBuilder = new StringBuilder("http://"
					+ ServerManager.SERVER_IP + ":" + ServerManager.HTTP_PORT + "/" + ServerManager.SERVER_NAME
					+ "/" + ServerManager.SERVLET_INFO_UPDATE + "?");
			stringBuilder.append("photo=" + name);
			URL url;
			HttpURLConnection conn;
			InputStream inputStream = null;
			try {
				url = new URL(stringBuilder.toString());
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setReadTimeout(TIME_OUT_MILLIS);
				conn.setRequestProperty("connection", "keep-alive");
				conn.setRequestProperty("Content-Type",
						"image/png; charset=utf-8");

				// get image from url
				inputStream = conn.getInputStream();
				byte[] imageByte = null;
				int result = 0, length = 0;
				byte[] buffer = new byte[1024];
				while ((result = inputStream.read(buffer)) != -1) {
					length += result;
					if (DEBUG)
						Log.e(TAG, "result " + result);
					if (imageByte == null)
						imageByte = Arrays.copyOfRange(buffer, 0, result);
					else {
						int last = imageByte.length;
						imageByte = Arrays.copyOf(imageByte, length);
						for (int i = 0; i < result; i++) {
							imageByte[last + i] = buffer[i];
						}
					}
					if (DEBUG)
						Log.e(TAG, "length: " + imageByte.length);
				}
				if (DEBUG)
					Log.e(TAG, "length: " + imageByte.length);
				SDCardManager sdcard = new SDCardManager();
				sdcard.saveImage(imageByte, SDCardManager.FILE_PHOTO, name);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return new String[] { id, name };
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String[] result) {
			if (DEBUG)
				Log.e(TAG, "onPostExecute");
			// send message to activity to update UI
			Message message = new Message();
			if (result[0].equals(mId)) {
				message.what = IMClientActivity.UIHandler.MESSAGE_USER_PHOTO_UPDATE;
				message.obj = result;
			} else {
				message.what = IMClientActivity.UIHandler.MESSAGE_FRAGMENT_CONTACTS_UPDATE_PHOTO;
				FriendUserBean user = new FriendUserBean();
				user.setId(result[0]);
				user.setImagePath(result[1]);
				message.obj = user;
			}
			
			try {
				mMessengerSend.send(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void onCancelled(String[] result) {
			super.onCancelled(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

	}

}
