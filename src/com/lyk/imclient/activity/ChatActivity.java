package com.lyk.imclient.activity;

import com.lyk.imclient.R;
import com.lyk.imclient.ui.fragment.PanelFragment;
import com.lyk.imclient.ui.view.ChatViewLayout;
import com.lyk.imclient.util.IPManager;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toolbar;

public class ChatActivity extends Activity {
	private static final String TAG = "ChatActivity";
	private static final boolean DEBUG = true;
	private static final boolean VIEW_DEBUG = true;
	
	private View mView;
	
	private EditText mMessageText;
	private ImageButton mVoiceButton;
	private ImageButton mChangeButton;
	private ImageButton mEmojiButton;

	private PanelFragment mPanelFragment;
	
	boolean isTyping;
	
	private OnClickListener sendlistener = new OnClickListener() {
		@Override
		public void onClick(View v) {
//			mChatViewLayout.sendMessage(mMessageText.getText().toString());
//			mMessageText.setText("");
		}

	};
	
	private OnClickListener mEditTextListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			if (!mPanelFragment.isHidden())
				ft.hide(mPanelFragment);
			ft.commit();
		}
		
	};

	private OnClickListener mLayoutListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (VIEW_DEBUG) Log.e(TAG, "View on click");
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mMessageText.getWindowToken(), 0);
		}
		
	};
	
	private OnClickListener mPanelListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (isTyping) {
				
			} else {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (mPanelFragment.isHidden()) {
					imm.hideSoftInputFromWindow(mMessageText.getWindowToken(), 0);
					ft.show(mPanelFragment);
					ft.commit();
				}
				else {
					ft.hide(mPanelFragment);
					ft.commit();
					imm.showSoftInput(mMessageText, InputMethodManager.SHOW_FORCED);
				}
			}
		}
		
	};
	
	private TextWatcher mTextChangeListener =  new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			if (VIEW_DEBUG) Log.e(TAG, "beforeTextChanged text : " + mMessageText.getText() + 
					" s : " + s + " start : " + start + " count : " + count +
					" after :" + after);
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (VIEW_DEBUG) Log.e(TAG, "onTextChanged text : " + mMessageText.getText() + 
					" s : " + s + " start : " + start + " before : " + before +
					" count :" + count);
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (VIEW_DEBUG) Log.e(TAG, "afterTextChanged text : " + mMessageText.getText() + 
					" s : " + s);
			if (s.length() == 0) {
				mChangeButton.setBackgroundResource(R.drawable.ic_add);
				isTyping = false;
			}
			else  {
				mChangeButton.setBackgroundResource(R.drawable.ic_send);
				isTyping = true;
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = LayoutInflater.from(this).inflate(R.layout.activity_chat, null);
		setContentView(mView);

		init();
	}
	
	private void init() {
		Toolbar toolbar = (Toolbar) mView.findViewById(R.id.chat_back);
		setActionBar(toolbar);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Mao");

		mMessageText = (EditText) mView.findViewById(R.id.edittext_activity_chat_send);
		mVoiceButton = (ImageButton) mView.findViewById(R.id.imagebutton_activity_chat_voice);
		mChangeButton = (ImageButton) mView.findViewById(R.id.imagebutton_activity_chat_change);
		mEmojiButton = (ImageButton) mView.findViewById(R.id.imagebutton_activity_chat_emoji);
		
		mMessageText.setOnClickListener(mEditTextListener);
		mMessageText.addTextChangedListener(mTextChangeListener);
		mChangeButton.setOnClickListener(mPanelListener);
		mView.setOnClickListener(mLayoutListener);
		
		FragmentManager fragmentManager = getFragmentManager();
		mPanelFragment = (PanelFragment) fragmentManager.findFragmentById(R.id.fragemnt_activity_chat_panel);
	}

	@Override
	public void onBackPressed() {
		if (!mPanelFragment.isHidden()) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.hide(mPanelFragment);
			ft.commit();
		} else {
			super.onBackPressed();
		}
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
