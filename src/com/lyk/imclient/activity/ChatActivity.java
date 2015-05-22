package com.lyk.imclient.activity;

import java.util.ArrayList;

import com.lyk.imclient.R;
import com.lyk.imclient.bean.MessageBean;
import com.lyk.imclient.receiver.ReceiveReceiver;
import com.lyk.imclient.receiver.ReceiveReceiver.OnMessageReceiveListener;
import com.lyk.imclient.receiver.SendReceiver;
import com.lyk.imclient.ui.adapter.ChatMessageAdapter;
import com.lyk.imclient.ui.fragment.PanelFragment;
import com.lyk.imclient.ui.fragment.RecordFragment;
import com.lyk.imclient.ui.view.BubbleReceiveView;
import com.lyk.imclient.ui.view.BubbleSendView;
import com.lyk.imclient.ui.view.ImageSendView;
import com.lyk.imclient.util.PlayManager;
import com.lyk.imclient.util.RecordManager;
import com.lyk.imclient.util.SDCardManager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toolbar;

public class ChatActivity extends Activity {
	private static final String TAG = "ChatActivity";
	private static final boolean DEBUG = true;
	private static final boolean VIEW_DEBUG = false;
	
	private View mView;
	private RecyclerView mChatView;
	private Context mContext;
	
	private EditText mMessageText;
	private ImageButton mVoiceButton;
	private ImageButton mChangeButton;
	private ImageButton mEmojiButton;

	private Fragment mCurrentFragment;
	private PanelFragment mPanelFragment;
	private RecordFragment mRecordFragment;
	
	private ActivityHandler mHandler;
	private ReceiveReceiver mReceiver;
	
	boolean isTyping;
	
	private OnClickListener mEditTextListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			if (!mCurrentFragment.isHidden())
				ft.hide(mCurrentFragment);
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
				// ChatService do something
				// View
				Message message = new Message();
				message.what = ActivityHandler.MESSAGE_SEND_TEXT;
				mHandler.sendMessage(message);
				
			} else {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (mCurrentFragment.isHidden()) {
					imm.hideSoftInputFromWindow(mMessageText.getWindowToken(), 0);
					ft.show(mCurrentFragment);
					ft.commit();
				}
				else {
					ft.hide(mCurrentFragment);
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
	
	private String mFriendId;
	private String mHostId;
	private String mName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		mView = LayoutInflater.from(this).inflate(R.layout.activity_chat, null);
		setContentView(mView);
		
		mFriendId = getIntent().getStringExtra("friend_id");
		mHostId = getIntent().getStringExtra("host_id");
		mName = getIntent().getStringExtra("name");
				
		init();
	}
	
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private ArrayList<View> mList;
	
	private void init() {
		Toolbar toolbar = (Toolbar) mView.findViewById(R.id.chat_back);
		setActionBar(toolbar);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(mName);

		mMessageText = (EditText) mView.findViewById(R.id.edittext_activity_chat_send);
		mVoiceButton = (ImageButton) mView.findViewById(R.id.imagebutton_activity_chat_voice);
		mChangeButton = (ImageButton) mView.findViewById(R.id.imagebutton_activity_chat_change);
		mEmojiButton = (ImageButton) mView.findViewById(R.id.imagebutton_activity_chat_emoji);
		mChatView = (RecyclerView) mView.findViewById(R.id.recyclerview_activity_chat_chat);
		
		mMessageText.setOnClickListener(mEditTextListener);
		mMessageText.addTextChangedListener(mTextChangeListener);
		mChangeButton.setOnClickListener(mPanelListener);
		mView.setOnClickListener(mLayoutListener);
		mVoiceButton.setOnClickListener(mVoiceListener);
		
		FragmentManager fragmentManager = getFragmentManager();
		mPanelFragment = (PanelFragment) fragmentManager.findFragmentById(R.id.fragemnt_activity_chat_panel);
		mRecordFragment = (RecordFragment) fragmentManager.findFragmentById(R.id.fragemnt_activity_chat_record);
		mCurrentFragment = mPanelFragment;
		
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.hide(mRecordFragment);
		fragmentTransaction.commit();
		
		mChatView.setHasFixedSize(true);
	    mLayoutManager = new LinearLayoutManager(this);
	    mChatView.setLayoutManager(mLayoutManager);
	    
	    mList = new ArrayList<View>();
	    mAdapter = new ChatMessageAdapter(mList);
	    mChatView.setAdapter(mAdapter);
	    
	    mRecordManager = new RecordManager();
	    mPlayManager = new PlayManager();
	    
	    mHandler = new ActivityHandler();
	    mReceiver = new ReceiveReceiver();
	    
	    mReceiver.setOnMessageReceiveListener(new OnMessageReceiveListener() {

			@Override
			public void onMessageReceive(byte[] data) {
				Message message = new Message();
				message.what = ActivityHandler.MESSAGE_RECEIVE_TEXT;
				message.obj = MessageBean.createMessage(data);
				mHandler.sendMessage(message);
			}
	    	
	    });
	    
	    IntentFilter filter = new IntentFilter();
	    filter.addAction(ReceiveReceiver.ACTION);
	    registerReceiver(mReceiver, filter);
	    
	}
	
	private RecordManager mRecordManager;
	private PlayManager mPlayManager;
	
	private OnClickListener mVoiceListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.hide(mCurrentFragment);
			if (mCurrentFragment == mPanelFragment) {
				mCurrentFragment = mRecordFragment;
				fragmentTransaction.show(mCurrentFragment);
				imm.hideSoftInputFromWindow(mMessageText.getWindowToken(), 0);
				mVoiceButton.setBackgroundResource(R.drawable.ic_keyboard);
			} else if (mCurrentFragment == mRecordFragment) {
				mCurrentFragment = mPanelFragment;
				imm.showSoftInput(mMessageText, InputMethodManager.SHOW_FORCED);
				mVoiceButton.setBackgroundResource(R.drawable.ic_mic);
			}
			fragmentTransaction.commit();
		}
		
	};

	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
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
	
	public Handler getHandler() {
		return mHandler;
	}

	public class ActivityHandler extends Handler {
		public static final int MESSAGE_SEND_TEXT = 0;
		public static final int MESSAGE_SEND_VOICE = 1;
		public static final int MESSAGE_SEND_IMAGE = 2;
		public static final int MESSAGE_RECEIVE_TEXT = 3;
		public static final int MESSAGE_RECEIVE_VOICE = 4;
		public static final int MESSAGE_RECEIVE_IMAGE = 5;

		@Override
		public void handleMessage(Message msg) {
			if (DEBUG) Log.e(TAG, "Message " + msg.what);
			switch(msg.what) {
			case MESSAGE_SEND_TEXT : {
				BubbleSendView sendView = new BubbleSendView(mContext);
				sendView.setSendMessage(mMessageText.getText().toString());
				sendView.setRecordTime(null);
				SDCardManager manager = new SDCardManager();
				Bitmap bitmap = manager.getBitmap(SDCardManager.FILE_PHOTO, mHostId + ".png");
				sendView.setHostPhoto(bitmap)
				sendView.setType(MessageType.MESSAGE_TEXT);
				mList.add(sendView);
				mAdapter.notifyItemInserted(mList.size());
				
				// send message to server
				MessageBean messageBean = MessageBean.createTextMessage(mHostId, mFriendId, mMessageText.getText().toString());
				Intent intent = new Intent(SendReceiver.ACTION);
				intent.putExtra("data", messageBean.getByteArray());
				sendBroadcast(intent);
				
				mMessageText.setText("");
				
			} break;
			case MESSAGE_SEND_VOICE : {
				if (DEBUG) Log.e(TAG, "MESSAGE_SEND_VOICE");
				String path = (String) msg.obj;
				BubbleSendView sendView = new BubbleSendView(mContext);
				sendView.setRecordPath(path);
				sendView.setType(MessageType.MESSAGE_MEDIA);
				sendView.setSendMessage("record");
				mPlayManager.prepare(path);
				sendView.setRecordTime("");
				SDCardManager manager = new SDCardManager();
				Bitmap bitmap = manager.getBitmap(SDCardManager.FILE_PHOTO, mHostId + ".png");
				sendView.setHostPhoto(bitmap);
//				if (DEBUG) Log.e(TAG, "width : " + sendView.getTextWidth());
				mList.add(sendView);
				mAdapter.notifyItemInserted(mList.size());
				// send message to server
				
			} break;
			case MESSAGE_SEND_IMAGE : {
				Bitmap bitmap = (Bitmap) msg.obj;
				ImageSendView sendView = new ImageSendView(mContext);
				sendView.setSendImage(bitmap);
				SDCardManager manager = new SDCardManager();
				Bitmap bitmap2 = manager.getBitmap(SDCardManager.FILE_PHOTO, mHostId + ".png");
				sendView.setHostPhoto(bitmap2);
				mList.add(sendView);
				mAdapter.notifyItemInserted(mList.size());
				// send message to server
			} break;
			case MESSAGE_RECEIVE_TEXT : {
				MessageBean messageBean = (MessageBean) msg.obj;
				BubbleReceiveView receiveView = new BubbleReceiveView(mContext);
				receiveView.setReceiveMessage(messageBean.getMessageText());
				receiveView.setRecordTime(null);
				SDCardManager manager = new SDCardManager();
				Bitmap bitmap = manager.getBitmap(SDCardManager.FILE_PHOTO, messageBean.getSendId() + ".png");
				receiveView.setFriendPhoto(bitmap);
				mList.add(receiveView);
				mAdapter.notifyItemInserted(mList.size());
			} break;
			case MESSAGE_RECEIVE_VOICE :
			case MESSAGE_RECEIVE_IMAGE : 
			}
		
		}
		
	}
	
	public class MessageType {
		private MessageType() {
		}
		
		public static final int MESSAGE_TEXT = 0;
		public static final int MESSAGE_MEDIA = 1;
	}
	
	
}
