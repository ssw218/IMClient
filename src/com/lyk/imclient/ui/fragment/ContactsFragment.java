package com.lyk.imclient.ui.fragment;

import com.lyk.imclient.R;
import com.lyk.imclient.util.IPManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ContactsFragment extends Fragment {

	private Button button;
	
	private TextView textView;
	
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			new IPManager().getNetworkIP();
		}
		
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab_contacts, container, false);
		button = (Button) view.findViewById(R.id.button);
		button.setOnClickListener(listener);
		textView = (TextView) view.findViewById(R.id.textview);
		return view;
	}
	
	
}