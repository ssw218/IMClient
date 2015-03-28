package com.lyk.imclient.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatSocket {
	private Socket client;
	
	public ChatSocket() {
		try {
			client = new Socket("111.164.214.156", 12345);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
