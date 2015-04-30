package com.lyk.imclient.provider;

import com.lyk.imclient.db.DataBaseHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class IMProvider extends ContentProvider {
	private static final String TAG = "IMProvider";
	private static final boolean DEBUG = true;
	
	private static final int VERSION = 1;
	
	public static final String DATABASE_NAME = "im.db";

	@Override
	public boolean onCreate() {
		DataBaseHelper helper = new DataBaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
