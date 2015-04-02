package com.lyk.imclient.activity;

import com.lyk.imclient.R;
import com.lyk.imclient.util.IPManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

public class ChatActivity extends Activity {
	
private Button button;
	
	private TextView textView;
	
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			new IPManager().getNetworkIP();
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.chat_back);
		setActionBar(toolbar);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Mao");
		
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(listener);
		textView = (TextView) findViewById(R.id.textview);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home: finish(); break;
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
