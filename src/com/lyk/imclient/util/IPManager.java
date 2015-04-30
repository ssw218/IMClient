package com.lyk.imclient.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Log;

public class IPManager {
	private static final String TAG = "IPManager";
	private static final boolean DEBUG = true;
	private static final boolean NET = true;
	
	private static final String API = "http://www.ip138.com/ip2city.asp";
	
	private String mNetworkIP;
	
	public IPManager() {
		
	}
	
	public String getHostIP() {
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}
	
	public void initNetwork() {
		if (NET) { 
			IPAsyncTask task = new IPAsyncTask();
			task.execute(API);
		}
	}
	
	public String getNetworkIP(){
		if (DEBUG) Log.e(TAG, "get network ip");
		if (NET) {
			long time = System.currentTimeMillis();
//			if (DEBUG) Log.e(TAG, "Network ip: " + mNetworkIP);
//			while (mNetworkIP == null) {};
//			System.out.println(System.currentTimeMillis() - time);
			if (DEBUG) Log.e(TAG, "Network ip: " + mNetworkIP);
			return mNetworkIP;
		} else {
			return getHostIP();
		}
	}
	
	class IPAsyncTask extends AsyncTask<String, String, StringBuilder> {
		private static final String TAG = "IPAsyncTask";
		private static final boolean DEBUG = false;
		
		@Override
		protected StringBuilder doInBackground(String... api) {
			if (DEBUG) Log.e(TAG, "doInBackground");
			mNetworkIP = null;
			StringBuilder webContent = new StringBuilder();
			try {
				URL url = new URL(API);
				InputStream inputStream = url.openStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String temp = null;
				while((temp = bufferedReader.readLine()) != null) {
					webContent.append(temp + "\r\n");
				}
				bufferedReader.close();
				inputStreamReader.close();
				inputStream.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			if (DEBUG) Log.e(TAG, "webContent : " + webContent);
			return webContent;
		}

		@Override
		protected void onPostExecute(StringBuilder result) {
			int start = result.indexOf("[");
			int end = result.indexOf("]");
			if (DEBUG) Log.e(TAG, "start: " + start + ", end: " + end);
			if(start < 0 || end < 0) {
				Log.e(TAG, "IPAsyncTask can't get network ip, please check the error");
			} else {
				mNetworkIP = result.substring(start + 1, end);
				Log.v(TAG, "IPAsyncTask get network IP : " + mNetworkIP);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled(StringBuilder result) {
			super.onCancelled(result);
		}
		
	}
}
