package com.lyk.imclient.ui.fragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.lyk.imclient.R;
import com.lyk.imclient.activity.IMClientActivity;
import com.lyk.imclient.activity.RegisterActivity;
import com.lyk.imclient.bean.UserBean;
import com.lyk.imclient.util.IPManager;
import com.lyk.imclient.util.ServerManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
	private static final String TAG = "LoginFragment";
	private static final boolean DEBUG = true;
	
	private View mView;
	private EditText mUserName;
	private EditText mPassword;
	private Button mLoginButton;
	private Button mRegisterButton;
	private LoginAsyncTask mLoginTask;
	
	String ip;
	IPManager ipManager = new IPManager();
	
	private OnClickListener mLoginListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (DEBUG) Log.e(TAG, "Login task : " + mLoginTask.isCancelled());
			if (!mLoginTask.isCancelled()) {
				if (DEBUG) Log.e(TAG, "on login listener");
				UserBean user = new UserBean();
				user.setUser(mUserName.getText().toString());
				user.setPassword(mPassword.getText().toString());
				user.setIp(ipManager.getNetworkIP());
				mLoginTask.execute(user);
			}
		}
		
	};
	
	private OnClickListener mRegisterListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), RegisterActivity.class);
			getActivity().startActivity(intent);
		}
		
	};
	
	public static LoginFragment getInstance(String params1, String params2) {
		LoginFragment fragment = new LoginFragment();
		return fragment;
	}
	
	public Fragment getFragment() {
		return this;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLoginTask = new LoginAsyncTask();
		ipManager.initNetwork();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mView = inflater.inflate(R.layout.fragment_login, container, false);
		mLoginButton = (Button) mView.findViewById(R.id.fragment_login_button_login);
		mRegisterButton = (Button) mView.findViewById(R.id.fragment_login_button_register);
		mLoginButton.setOnClickListener(mLoginListener);
		mRegisterButton.setOnClickListener(mRegisterListener);
		mUserName = (EditText) mView.findViewById(R.id.fragment_login_edittext_user);
		mPassword = (EditText) mView.findViewById(R.id.fragment_login_edittext_password);
		return mView;
	}
	
	class LoginAsyncTask extends AsyncTask<UserBean, Void, Boolean> {
		private static final String TAG = "LoginAsyncTask";
		private static final int TIME_OUT_MILLIS = 20000;
		
		@Override
		protected Boolean doInBackground(UserBean... params) {
			StringBuilder s = new StringBuilder("http://" + ServerManager.SERVER_IP + 
					"/" + ServerManager.SERVER_NAME +
					"/" + ServerManager.SERVLET_LOGIN + "?");
			
			if (DEBUG) Log.v(TAG, params[0].toString());
			
			s.append("username=" + params[0].getUser());
			s.append("&password=" + params[0].getPassword());
			s.append("&ip=" + params[0].getIp());
			
			URL url;
			InputStream inputStream = null;
			BufferedReader reader = null;
			
			try {
				url = new URL(s.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setReadTimeout(TIME_OUT_MILLIS);
//				OutputStream outputStream = conn.getOutputStream();
//				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
				inputStream = conn.getInputStream();
				reader = new BufferedReader(new InputStreamReader(inputStream));
				Log.v(TAG, "response code : " + conn.getResponseCode() +
						" response message : " + conn.getResponseMessage());
				String result = reader.readLine();
				if (DEBUG) Log.v(TAG, result);
				if (result.equals("false"))
					return false;
				if (conn.getResponseCode() == 200)
					return true;
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
			
			return false;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			Log.e(TAG, "result: " + result);
			if (result) {
				IMClientActivity activity = (IMClientActivity) getActivity();
				activity.hideLoginFragment(getFragment());
			} else {
				Toast.makeText(getActivity(), "’À∫≈ªÚ√‹¬Î¥ÌŒÛ£°", Toast.LENGTH_LONG);
			}
		}

		@Override
		protected void onCancelled(Boolean result) {
			super.onCancelled(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		
	}
	
}
