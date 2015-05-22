package com.lyk.imclient.util;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class PlayManager {
	private MediaPlayer mMediaPlayer;
	private SDCardManager mSDCardManager;
	
	public PlayManager() {
		mMediaPlayer = new MediaPlayer();
		mSDCardManager = new SDCardManager();
	}
	
	public void prepare(String path) {
		try {
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		mMediaPlayer.start();
	}
	
	public int getMediaSecond() {
		return mMediaPlayer.getDuration() / 1000;
	}
}
