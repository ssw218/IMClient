package com.lyk.imclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class IMDBOpenHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "im.db";
	// id username image_url image_path introduce 
	private static final String DATABASE_CREATE = "create table " + 
			DATABASE_NAME + " (" + "" ;

	public IMDBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
