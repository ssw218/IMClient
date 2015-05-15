package com.lyk.imclient.util;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

public class RecordManager {
	private MediaRecorder mMediaRecorder;
	
	public int getVoiceLevel() {
		// 60
		
		return 0;
	}
	
	public void prepare() {
		
	}
	
	public void start() {
		
	}
	
	public void cancel() {
		
	}
	
	public void send() {
		
	}
	
	public class State {
		public static final int PREPARED = 0;
		public static final int RECORDING = 1;
		public static final int CANCLING = 2;
		public static final int FINISHED = 3;
	}
}
