package com.lyk.imclient.service;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.IBinder;

public class ChatsService extends Service {
	private static final String TAG = "ChatsService";
	private static final boolean DEBUG = true;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
 
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	class TCPThread extends Thread {

		@Override
		@Deprecated
		public void destroy() {
			super.destroy();
		}

		@Override
		public void run() {
			super.run();
		}
		
	}

	final class TCPClient extends Client {
		private static final String TAG = "TCPClient";
		private static final String TEST_SERVER_IP = "123.151.91.4";

		public TCPClient(long endTime)
				throws IOException {
			super(SocketChannel.open(), endTime);
		}
		
		void bind(SocketAddress address) throws IOException {
			SocketChannel channel = (SocketChannel) mSelectionKey.channel();
			channel.socket().bind(address);
		}
		
		void connect(SocketAddress address) throws IOException {
			SocketChannel channel = (SocketChannel) mSelectionKey.channel();
			if (channel.connect(address))
				return ;
			mSelectionKey.interestOps(SelectionKey.OP_CONNECT);
			try {
				while (!channel.finishConnect()) {
					if (!mSelectionKey.isConnectable())
						blockUntil(mSelectionKey, mEndTime);
				}
			} finally {
				if (mSelectionKey.isValid())
					mSelectionKey.interestOps(0);
			}
		}
		
		void write(byte[] data) {
			SocketChannel channel = (SocketChannel) mSelectionKey.channel();
			
		}
		
		
		
	}
	
	static class Client {
		protected SelectionKey mSelectionKey;
		protected long mEndTime;
		
		protected Client(SelectableChannel channel, long endTime) throws IOException {
			boolean done = false;
			Selector selector = null;
			mEndTime = endTime;
			try {
				selector = Selector.open();
				channel.configureBlocking(false);
				mSelectionKey = channel.register(selector, SelectionKey.OP_READ);
				done = true;
			} finally {
				if (!done && selector != null)
					selector.close();
				if (!done)
					channel.close();
			}
		}
		
		protected static void blockUntil(SelectionKey key, long endTime) throws IOException {
			long timeout = endTime - System.currentTimeMillis();
			int nkeys = 0;
			if (timeout > 0) {
				nkeys = key.selector().select(timeout);
			} else if (timeout == 0) {
				nkeys = key.selector().selectNow();
			} 
			
			if (nkeys == 0)
				throw new SocketTimeoutException();
		}
	}
	
}
