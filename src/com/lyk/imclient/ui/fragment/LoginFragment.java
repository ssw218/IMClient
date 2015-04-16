package com.lyk.imclient.ui.fragment;

import com.lyk.imclient.R;
import com.lyk.imclient.activity.IMClientActivity;
import com.lyk.imclient.activity.RegisterActivity;
import com.lyk.imclient.bean.UserBean;
import com.lyk.imclient.util.IPManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {
	private static final String TAG = "LoginFragment";
	private static final boolean DEBUG = true;
	
	private View mView;
	private EditText mUserName;
	private EditText mPassword;
	private Button mLoginButton;
	private Button mRegisterButton;
	private LoginAsyncTask mLoginTask;
	
	private OnClickListener mLoginListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mLoginTask.isCancelled()) {
				UserBean user = new UserBean();
				user.setUser(mUserName.getText().toString());
				user.setPassword(mPassword.getText().toString());
				user.setIp(new IPManager().getNetworkIP());
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
		
		@Override
		protected Boolean doInBackground(UserBean... params) {
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				IMClientActivity activity = (IMClientActivity) getActivity();
				activity.hideLoginFragment(getFragment());
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
