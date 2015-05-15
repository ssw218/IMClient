package com.lyk.imclient.ui.fragment;

import com.lyk.imclient.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class RecordFragment extends Fragment {
	private static final String TAG = "RecordFragment";
	private static final boolean DEBUG = true;
	
	private ImageButton mRecordButton;
	
	private OnTouchListener mRecordListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (DEBUG) Log.e(TAG, "event : " + event.getAction());
			switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN :
					// record start
					// show dialog
				case MotionEvent.ACTION_MOVE :
					// prepare or continue
					// change dialog view
				case MotionEvent.ACTION_UP :
					// send or cancel
					// canel dialog
			}
			return true;
		}
	};

	public RecordFragment() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_record, null);
		mRecordButton = (ImageButton) view.findViewById(R.id.imagebutton_fragment_record_button);
		mRecordButton.setOnTouchListener(mRecordListener);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	
}
