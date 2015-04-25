package com.lyk.imclient.service;

import android.os.RemoteException;

import com.lyk.imclient.aidl.IXMPPConnection;;

public class XMPPConnectionAdapter extends IXMPPConnection.Stub {

	@Override
	public boolean connect() throws RemoteException {
		return false;
	}

	@Override
	public boolean login() throws RemoteException {
		return false;
	}

	@Override
	public boolean disconnect() throws RemoteException {
		return false;
	}

}
