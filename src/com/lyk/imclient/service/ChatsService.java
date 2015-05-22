package com.lyk.imclient.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import com.lyk.imclient.bean.MessageBean;
import com.lyk.imclient.receiver.ReceiveReceiver;
import com.lyk.imclient.receiver.SendReceiver;
import com.lyk.imclient.receiver.SendReceiver.OnMessageSendListener;
import com.lyk.imclient.util.ServerManager;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class ChatsService extends Service {
	private static final String TAG = "ChatsService";
	private static final boolean DEBUG = true;
	
	private TCPClient mClient;
	private ChatThread mChatThread;
	
	private String mHostId;
	
	private SendReceiver mReceiver;
	
	private OnMessageSendListener mMessageSendListener = new OnMessageSendListener() {

		@Override
		public void onMessageSend(byte[] data) {
			new WriteThread(data).start();
		}
		
	};
	
	class WriteThread extends Thread {
		private byte[] data;
		public WriteThread(byte[] data) {
			this.data = data;
		}
		
		public void run() {
			try {
//				if (DEBUG) Log.v(TAG, Arrays.toString(data));
				mClient.write(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mChatThread = new ChatThread();
		mReceiver = new SendReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(SendReceiver.ACTION);
		registerReceiver(mReceiver, filter);
		mReceiver.setOnMessageSendListener(mMessageSendListener);
	}
 
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mHostId = intent.getStringExtra("id");
		mChatThread.start();
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	class ChatThread extends Thread {
		public void run() {
			try {
				mClient = new TCPClient();
				Timer timer = new Timer();
				timer.schedule(new HeartTask(), 0, 1000);
				ReadThread readThread = new ReadThread();
				readThread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class HeartTask extends TimerTask {

		@Override
		public void run() {
			MessageBean messageBean = MessageBean.createHeartMessage(mHostId);
			//if (DEBUG) Log.e(TAG, "byte[] : " + Arrays.toString(messageBean.getByteArray()));
			try {
//				if (DEBUG) Log.v(TAG, "HostId : " + mHostId + " " + Arrays.toString(messageBean.getByteArray()));
				mClient.write(messageBean.getByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class ReadThread extends Thread {
		private static final String TAG = "ReadThread";
		private static final boolean DEBUG = true;
		
		@Override
		public void run() {
//			byte[] buffer = null;
			try {
				while (mClient.mSelector.select() > 0) {
					if (DEBUG) Log.e(TAG, " In while 135");
					for (SelectionKey next : mClient.mSelector.selectedKeys()) {
						if (next.isReadable()) {
							SocketChannel socketChannel = (SocketChannel) next.channel();
							ByteBuffer buffer = ByteBuffer.allocate(500);
							buffer.clear();
							int length = socketChannel.read(buffer);
//							if (DEBUG) Log.e(TAG, Arrays.toString(buffer.array()));
							if (length == -1) continue;
							buffer.flip();
							if (buffer.get(0) == MessageBean.MESSAGE_TYPE_TEXT) {
								MessageBean messageBean = MessageBean.createMessage(buffer.array());
								// send message to activity
								Intent intent = new Intent(ReceiveReceiver.ACTION);
								intent.putExtra("data", messageBean.getByteArray());
								sendBroadcast(intent);
								next.interestOps(SelectionKey.OP_READ);
								if (DEBUG) 
									Log.e(TAG, "message : " + messageBean.getSendId() + " " + 
											messageBean.getReceiveId() + " " + messageBean.getSendMessageLength() + " " +
											messageBean.getMessageText());
							}
							mClient.mSelector.selectedKeys().remove(next);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	final class TCPClient {
		private static final String TAG = "TCPClient";
//		private static final String TEST_SERVER_IP = "123.151.91.4";
		private Selector mSelector;
		private SocketChannel mSocketChannel;
		
		private ReadThread mThread;
		
		public TCPClient() throws IOException {
			InetSocketAddress address = new InetSocketAddress(ServerManager.SERVER_IP, ServerManager.SOCKET_PORT);
			mSocketChannel = SocketChannel.open(address);
			mSocketChannel.configureBlocking(false);
			mSelector = Selector.open();
			mSocketChannel.register(mSelector, SelectionKey.OP_READ);
			mThread = new ReadThread();
			mThread.start();
		}
		
		public byte[] read() throws IOException {
			ByteBuffer buffer = ByteBuffer.allocate(500);
			byte[] message = null;
			int read;
			while ((read = mSocketChannel.read(buffer)) != -1) {
				message = buffer.array();
//				if (DEBUG) Log.v(TAG, Arrays.toString(message));
				if (buffer.get(0) == 0) {
					continue;
				} else if (buffer.get(0) == 1) {
					
				} else if (buffer.get(0) == 2) {
					
				} else if (buffer.get(0) == 3) {
					
				} 
			}
			if (DEBUG) Log.v(TAG, Arrays.toString(message));
			return message;
		}
		
		void bind(SocketAddress address) throws IOException {
//			SocketChannel channel = (SocketChannel) mSelectionKey.channel();
//			channel.socket().bind(address);
		}
		
		void connect(SocketAddress address) throws IOException {
			
		}
		
		void write(byte[] data) throws IOException {
			ByteBuffer buffer = ByteBuffer.wrap(data);
			mSocketChannel.write(buffer);
		}
		
		
		
	}
	
}
