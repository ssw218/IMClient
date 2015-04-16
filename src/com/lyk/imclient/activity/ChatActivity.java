package com.lyk.imclient.activity;

import com.lyk.imclient.R;
import com.lyk.imclient.ui.view.ChatViewLayout;
import com.lyk.imclient.util.IPManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

public class ChatActivity extends Activity {

	private Button mSend;
	private EditText mMessage;
	private ChatViewLayout mChatViewLayout;

	private OnClickListener sendlistener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mChatViewLayout.sendMessage(mMessage.getText().toString());
			mMessage.setText("");
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

		mSend = (Button) findViewById(R.id.button_activity_chat_send);
		mSend.setOnClickListener(sendlistener);
		mMessage = (EditText) findViewById(R.id.edittext_activity_chat_send);
		mChatViewLayout = (ChatViewLayout) findViewById(R.id.layout_activity_chat_chat);
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
