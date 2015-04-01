package com.lyk.imclient.aidl;

interface IXMPPConnection {
	boolean connect();
	
	boolean login();
	
	boolean disconnect();
}
