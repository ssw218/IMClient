package com.lyk.imclient.bean;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import android.util.Log;

public class MessageBean {
	private static final String TAG = "MessageBean";
	private static final boolean DEBUG = true;
	
	public static final byte MESSAGE_TYPE_HEART = 0;
	public static final byte MESSAGE_TYPE_TEXT = 1;
	public static final byte MESSAGE_TYPE_IMAGE = 2;
	public static final byte MESSAGE_TYPE_MEDIA = 3;
	
	public static final int BUFFER_LENGTH = 500;
	public static final int MAX_TEXT_BYTE = 400;
	
	private static final int BEGIN_TYPE = 0;
	private static final int BEGIN_SEND_ID = 1;
	private static final int BEGIN_RECEIVE_ID = 5;
	private static final int BEGIN_SEND_TIME = 9;
	private static final int BEGIN_SEND_MESSAGE_LENGTH = 17;
	private static final int BEGIN_SEND_MESSAGE = 19;
	
	private static final String TEXT_ENCODE = "UTF-8";
	
	private byte[] mData;
	
	private MessageBean() {
		mData = new byte[BUFFER_LENGTH];
	}
	
	public static MessageBean createMessage(byte[] data) {
		MessageBean message = new MessageBean();
		message.mData = data;
		return message;
	}
	
	public static MessageBean createHeartMessage(String sid) {
		MessageBean message = new MessageBean();
		int id = Integer.parseInt(sid);
		message.setMessageType(MESSAGE_TYPE_HEART);
		message.setSendId(id);
		message.setSendTime(System.currentTimeMillis());
		return message;
	}
	
	public static MessageBean createTextMessage(String sid, String rid, String text) {
		MessageBean message = new MessageBean();
		message.setMessageType(MESSAGE_TYPE_TEXT);
		message.setSendId(Integer.parseInt(sid));
		message.setReceiveId(Integer.parseInt(rid));
		message.setSendTime(System.currentTimeMillis());
		message.setSendText(text);
		return message;
	}
	
	private void setSendText(String text) {
		byte[] btext = null;
		try {
			btext = text.getBytes(TEXT_ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// judge length
		
		setSendTextLength(btext.length);
		for (int i = 0; i < btext.length; i++) {
			mData[i + BEGIN_SEND_MESSAGE] = btext[i];
		}
		
	}
	
	private void setSendTextLength(int length) {
		byte[] bl = new byte[2];
		bl[0] = (byte) (length & 0xFF);
		bl[1] = (byte) ((length >> 8) & 0xFF);
		for(int i = 0; i < bl.length; i++) {
			mData[i + BEGIN_SEND_MESSAGE_LENGTH] = bl[i];
		}
	}
	
	public int getSendMessageLength() {
		byte[] bl = Arrays.copyOfRange(mData, BEGIN_SEND_MESSAGE_LENGTH, BEGIN_SEND_MESSAGE_LENGTH + 2);
		int[] length = new int[2];
		if (bl[0] < 0) 
			length[0] = (bl[0] & 0x7F) + 0x80;
		else length[0] = bl[0];
		if (bl[1] < 0) 
			length[1] = (bl[1] & 0x7F) + 0x80;
		else length[1] = bl[1];
		length[1] = length[1] << 8;
		if (DEBUG) Log.v(TAG, "length: " + length[0] + length[1]);
		return length[0] + length[1];
	}
	
	private void setMessageType(byte type) {
//		if(DEBUG) Log.v(TAG, "mData : " + mData);
		mData[BEGIN_TYPE] = type;
	}
	
	private void setSendId(int id) {
		// 						      00000000
		// 00000000 00000000 00000000 00000000
		byte[] bid = intToByte(id);
		for(int i = 0; i < bid.length; i++) {
			mData[i + BEGIN_SEND_ID] = bid[i];
		}
//		if (DEBUG) Log.e(TAG, "id : " + id + " " + Arrays.toString(bid));
	}
	
	private void setReceiveId(int id) {
		byte[] bid = intToByte(id);
		for(int i = 0; i < bid.length; i++) {
			mData[i + BEGIN_RECEIVE_ID] = bid[i];
		}
	}
	
	private void setSendTime(long time) {
		byte[] btime = longToByte(time);
		for(int i = 0; i < btime.length; i++) {
			mData[i + BEGIN_SEND_TIME] = btime[i];
		}
	}
	
	public long getSendTime() {
		byte[] btime = new byte[8];
		for(int i = 9; i < 9 + 8; i++)
			btime[i - 9] = mData[i];
		long time = byteToLong(btime);
		return time;
	}
	
	public String getMessageText() {
		int length = getSendMessageLength();
		byte[] btext = Arrays.copyOfRange(mData, BEGIN_SEND_MESSAGE , BEGIN_SEND_MESSAGE + length);
		String text = null;
		try {
			text = new String(btext, TEXT_ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public byte getType() {
		return mData[0];
	}
	
	public int getSendId() {
		byte[] bn = new byte[4];
		bn[0] = mData[1];
		bn[1] = mData[2];
		bn[2] = mData[3];
		bn[3] = mData[4];
		return byteToInt(bn);
	}
	
	public int getReceiveId() {
		byte[] bn = new byte[4];
		bn[0] = mData[5];
		bn[1] = mData[6];
		bn[2] = mData[7];
		bn[3] = mData[8];
		return byteToInt(bn);
	}
	
	public byte[] getByteArray() {
		return mData;
	}
	
	private byte[] longToByte(long n) {
		byte[] bn = new byte[8];
		bn[0] = (byte) (n & 0xFF);
		bn[1] = (byte) ((n >> 8) & 0xFF);
		System.out.println();
		bn[2] = (byte) ((n >> 16) & 0xFF);
		bn[3] = (byte) ((n >> 24) & 0xFF);
		bn[4] = (byte) ((n >> 32) & 0xFF);
		bn[5] = (byte) ((n >> 40) & 0xFF);
		bn[6] = (byte) ((n >> 48) & 0xFF);
		bn[7] = (byte) ((n >> 56) & 0xFF);
//		System.out.println(Arrays.toString(bn));
		return bn;
	}
	
	private Long byteToLong(byte[] bn) {
		long n[] = new long[8];
		for (int i = 0; i < 8; i++) {
			if (bn[i] < 0) 
				n[i] = (bn[i] & 0x7F) + 0x80;
			else
				n[i] = bn[i];
//			System.out.println("b " + n[i]);
			n[i] = n[i] << (i * 8);
//			System.out.println("a " + n[i]);
		}
//		System.out.println(Arrays.toString(n));
		long result = 0;
		for (long next : n) 
			result += next;
		return result;
	} 
	
	private byte[] intToByte(int n) {
		byte[] bn = new byte[4];
		bn[0] = (byte) (n & 0x000000FF);
		bn[1] = (byte) ((n & 0x0000FF00) >> 8);
		bn[2] = (byte) ((n & 0x00FF0000) >> 16);
		bn[3] = (byte) ((n & 0xFF000000) >> 24);
//		System.out.println(Arrays.toString(bn));
		return bn;
	}
	
	private int byteToInt(byte[] bn) {
		int n[] = new int[4];
		for (int i = 0; i < 4; i++) {
			if (bn[i] < 0) 
				n[i] = (bn[i] & 0x7F) + 0x80;
			else
				n[i] = bn[i];
			switch (i) {
			case 0:
				break;
			case 1:
				n[i] = n[i] << 8;
				break;
			case 2:
				n[i] = n[i] << 16;
				break;
			case 3:
				n[i] = n[i] << 24;
				break;
			}
		}
//		System.out.println(Arrays.toString(n));
		return n[0] + n[1] + n[2] + n[3];
	} 
	
}
