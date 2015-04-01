package com.lyk.imclient.service;

import android.os.RemoteException;

import com.lyk.imclient.aidl.IXMPPConnection;;

public class XMPPConnectionAdapter extends IXMPPConnection.Stub {

	@Override
	public boolean connect() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean login() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disconnect() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
