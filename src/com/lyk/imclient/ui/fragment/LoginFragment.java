package com.lyk.imclient.ui.fragment;

import com.lyk.imclient.R;
import com.lyk.imclient.activity.IMClientActivity;
import com.lyk.imclient.activity.RegisterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class LoginFragment extends Fragment {
	private View mView;
	private Button mLoginButton;
	private Button mRegisterButton;
	
	private OnClickListener mLoginListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			IMClientActivity activity = (IMClientActivity) getActivity();
			activity.hideLoginFragment(getFragment());
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
		Bundle args = new Bundle();
		args.putString("username", params1);
		args.putString("password", params2);
		fragment.setArguments(args);
		return fragment;
	}
	
	public Fragment getFragment() {
		return this;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mView = inflater.inflate(R.layout.fragment_login, container, false);
		mLoginButton = (Button) mView.findViewById(R.id.button_login);
		mRegisterButton = (Button) mView.findViewById(R.id.button_register);
		mLoginButton.setOnClickListener(mLoginListener);
		mRegisterButton.setOnClickListener(mRegisterListener);
		return mView;
	}
	
}
