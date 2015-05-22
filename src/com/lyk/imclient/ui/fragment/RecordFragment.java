package com.lyk.imclient.ui.fragment;

import java.io.IOException;

import com.lyk.imclient.R;
import com.lyk.imclient.activity.ChatActivity;
import com.lyk.imclient.activity.ChatActivity.ActivityHandler;
import com.lyk.imclient.util.PlayManager;
import com.lyk.imclient.util.RecordManager;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RecordFragment extends Fragment {
	private static final String TAG = "RecordFragment";
	private static final boolean DEBUG = false;
	
	private ImageButton mRecordButton;
	private TextView mPoint;
	private TextView mTime;
	
	private RecordHandler mHandler;
	private TimeThread mThread;
	
	private RecordManager mRecordManager;
	private PlayManager mPlayManager;
	
	private OnTouchListener mRecordListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// if (DEBUG) Log.e(TAG, "event : " +
			// event.actionToString(event.getAction()));
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// record start
				Message message3 = new Message();
				message3.what = RecordHandler.MESSAGE_START_RECORD;
				mHandler.sendMessage(message3);
				// show message
				mThread = new TimeThread(System.currentTimeMillis());
				mThread.start();
				mPoint.setText("手指上划，取消发送");
				break;
			case MotionEvent.ACTION_MOVE:
				// prepare or continue
				break;
			case MotionEvent.ACTION_UP:
				// send or cancel
				// canel dialog
				mThread.mDown = true;
				mThread = null;
				mEvent = event;

			}
			return true;
		}
	};
	
	MotionEvent mEvent = null;

	public RecordFragment() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHandler = new RecordHandler();
		mRecordManager = new RecordManager();
		mPlayManager = new PlayManager();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_record, null);
		mRecordButton = (ImageButton) view.findViewById(R.id.imagebutton_fragment_record_button);
		mRecordButton.setOnTouchListener(mRecordListener);
		mPoint = (TextView) view.findViewById(R.id.textview_fragment_record_point);
		mTime = (TextView) view.findViewById(R.id.textView_fragment_record_time);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	
	class TimeThread extends Thread {
		private long mBeginTime;
		private boolean mDown;
		
		public TimeThread(long time) {
			super();
			mBeginTime = time;
			mDown = false;
		}
		
		public void run() {
			while(!mDown && System.currentTimeMillis() - mBeginTime <= 60000) {
				long time = System.currentTimeMillis() - mBeginTime;
				
				if (time % 1000 == 0) {
//					if (DEBUG) Log.e(TAG, "time : " + time / 1000);
					Message message = new Message();
					message.what = RecordHandler.MESSAGE_UPDATE_TIMR;
					message.arg1 = (int) (time / 1000);
					mHandler.sendMessage(message);
				}
			}
			
			// stop record
			Message message2 = new Message();
			if (System.currentTimeMillis() - mBeginTime < 1000) {
				message2.what = RecordHandler.MESSAGE_CANCEL_RECORD;
			} else {
				message2.what = RecordHandler.MESSAGE_END_RECORD;
			}
			mHandler.sendMessage(message2);
		}
	}
	
	String path = null;
	
	class RecordHandler extends Handler {
		public static final int MESSAGE_UPDATE_TIMR = 0;
		public static final int MESSAGE_END_RECORD = 1;
		public static final int MESSAGE_START_RECORD = 2;
		public static final int MESSAGE_CANCEL_RECORD = 3;
		public static final int MESSAGE_DELETE_RECORD = 4;
		
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_UPDATE_TIMR : 
				mTime.setText(msg.arg1 + "''"); 
				break;
			case MESSAGE_END_RECORD : 
				if (DEBUG) Log.e(TAG, "MESSAGE_END_RECORD");
				mPoint.setText("按住话筒，开始录音");
				mTime.setText("");
				if (mEvent.getY() < -50) {
					// cancel
					mRecordManager.cancel();
				} else {
					// send
					mRecordManager.stop();
					Message message = new Message();
					message.what = ActivityHandler.MESSAGE_SEND_VOICE;
					message.obj = mRecordManager.getPath();
					((ChatActivity) getActivity()).getHandler().sendMessage(message);
				}
				
				break;
			case MESSAGE_START_RECORD :
				try {
					mRecordManager.prepare();
					mRecordManager.start();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case MESSAGE_CANCEL_RECORD : 
				Toast.makeText(getActivity(), "录音时间太短", Toast.LENGTH_SHORT);
				mRecordManager.cancel();
				mPoint.setText("按住话筒，开始录音");
				mTime.setText("");
				break;
			}
			
		}
		
	}
	
}
