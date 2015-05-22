package com.lyk.imclient.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.media.MediaRecorder;
import android.text.format.DateFormat;

public class RecordManager {
	private MediaRecorder mMediaRecorder;
	private SDCardManager mSDCardManager;
	
	public RecordManager() {
		mSDCardManager = new SDCardManager();
	}
	
	private String path = null;
	
	public void prepare() throws IllegalStateException, IOException {
		mMediaRecorder = new MediaRecorder();
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		path = mSDCardManager.getFilePath(SDCardManager.FILE_AUDIO);
		path += "/" + DateFormat.format("yyyyMMddhhmmss", Calendar.getInstance(Locale.CHINA)) + ".mp4"; 
		mMediaRecorder.setOutputFile(path);
		mMediaRecorder.prepare();
	}
	
	public void start() {
		mMediaRecorder.start();
	}
	
	public String getPath() {
		return path;
	}
	
	public void cancel(String path) {
		mMediaRecorder.setOnErrorListener(null);
		mMediaRecorder.stop();
		mMediaRecorder.release();
		File file = new File(path);
		file.delete();
	}
	
	public void cancel() {
		mMediaRecorder.setOnErrorListener(null);
		mMediaRecorder.stop();
		mMediaRecorder.release();
		File file = new File(path);
		file.delete();
	}
	
	public void stop() {
		mMediaRecorder.setOnErrorListener(null);
		mMediaRecorder.stop();
		mMediaRecorder.release();
	}
	
	public class State {
		public static final int PREPARED = 0;
		public static final int RECORDING = 1;
		public static final int CANCLING = 2;
		public static final int FINISHED = 3;
	}
}
