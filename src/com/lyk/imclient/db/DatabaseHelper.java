package com.lyk.imclient.db;

import java.io.File;

import com.lyk.imclient.util.SDCardManager;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DatabaseHelper {
	private static final String TAG = "DataBaseHelper";
	private static final boolean DEBUG = true;
	private static final int VERSION = 4;
	private static final String DATABASE_NAME = "im.db";

	private static DatabaseHelper mDatabaseHelper;
	
	private UserInfoHelper mUserInfoHelper;
	private MessageHelper mMessageHelper;

	public synchronized static DatabaseHelper getInstance(Context context) {
		if (mDatabaseHelper == null)
			mDatabaseHelper = new DatabaseHelper(context);
		return mDatabaseHelper;
	}
	
	private DatabaseHelper(Context context) {
		mUserInfoHelper = new UserInfoHelper(context);
		mMessageHelper = new MessageHelper(context);
	}
	
	public class UserInfoHelper extends SQLiteOpenHelper {
		private static final String TAG = "UserInfoHelper";
		private static final boolean DEBUG = true;

		private static final String TABLE_NAME = "user_info";
		// user_info
		// id user_name image_url image_path introduce
		private static final String COLUMN_NAME_ID = "id";
		private static final String COLUMN_NAME_USER_NAME = "user_name";
		private static final String COLUMN_NAME_IMAGE_URL = "image_url";
		private static final String COLUMN_NAME_IMAGE_PATH = "image_path";
		private static final String COLUMN_NAME_INTRODUCE = "introduce";

		public UserInfoHelper(Context context) {
			super(context, DATABASE_NAME, null, VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME_ID
					+ " INTEGER PRIMARY KEY, " + COLUMN_NAME_USER_NAME
					+ " TEXT, " + COLUMN_NAME_IMAGE_URL + " TEXT, "
					+ COLUMN_NAME_IMAGE_PATH + " TEXT, "
					+ COLUMN_NAME_INTRODUCE + " TEXT" + ");");
		}
		
		// for test
		public void select() {
			if (DEBUG) Log.e(TAG, "select()");
			String[] columns = new String[]{COLUMN_NAME_ID, COLUMN_NAME_USER_NAME, 
					COLUMN_NAME_IMAGE_URL, COLUMN_NAME_IMAGE_PATH, COLUMN_NAME_INTRODUCE};
			String selection = COLUMN_NAME_ID + "=" + 1;
			String selectionArgs[] = null;
			String groupBy = null;
			String having = null;
			String orderBy = null;
			
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
			
			if (cursor.moveToFirst()) {
				for (String next : columns) {
					int columnIndex = cursor.getColumnIndex(next);
					if (columnIndex > -1) {
						if (next.equals(COLUMN_NAME_ID)) {
							int columnValue = cursor.getInt(columnIndex);
							if (DEBUG) Log.e(TAG, " " + columnValue);
						} else if (next.equals(COLUMN_NAME_USER_NAME)) {
							String columnValue = cursor.getString(columnIndex);
							if (DEBUG) Log.e(TAG, " " + columnValue);
						} else if (next.equals(COLUMN_NAME_IMAGE_URL)) {
							String columnValue = cursor.getString(columnIndex);
							if (DEBUG) Log.e(TAG, " " + columnValue);
						} else if (next.equals(COLUMN_NAME_IMAGE_PATH)) {
							String columnValue = cursor.getString(columnIndex);
							if (DEBUG) Log.e(TAG, " " + columnValue);
						} else if (next.equals(COLUMN_NAME_IMAGE_URL)) {
							String columnValue = cursor.getString(columnIndex);
							if (DEBUG) Log.e(TAG, " " + columnValue);
						} else if (next.equals(COLUMN_NAME_INTRODUCE)) {
							String columnValue = cursor.getString(columnIndex);
							if (DEBUG) Log.e(TAG, " " + columnValue);
						}
						
					}
				}
			}
			cursor.close();
		}
		
		// for test
		public void insert() {
			if (DEBUG) Log.e(TAG, "insert()");
			String nullColumnHack = null;
			ContentValues values = new ContentValues();
			values.put(COLUMN_NAME_ID, 1);
			values.put(COLUMN_NAME_USER_NAME, "Li Yikun");
			values.put(COLUMN_NAME_IMAGE_URL, "cat.png");
			values.put(COLUMN_NAME_IMAGE_PATH, "cat.png");
			values.put(COLUMN_NAME_INTRODUCE, "fall in love with Miscrosoft OneNote");
			SQLiteDatabase db = getWritableDatabase();
			db.insertOrThrow(TABLE_NAME, nullColumnHack, values);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (newVersion > oldVersion) {
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
				onCreate(db);
			}
		}

	}

	public class MessageHelper extends SQLiteOpenHelper {
		private static final String TAG = "MessageHelper";
		private static final boolean DEBUG = true;

		private static final String TABLE_NAME = "messages_friend";
		// messages_friend
		// id id_send id_receive message time
		private static final String COLUMN_NAME_ID = "id";
		private static final String COLUMN_NAME_ID_SEND = "id_send";
		private static final String COLUMN_NAME_ID_RECEIVE = "id_receive";
		private static final String COLUMN_NAME_MESSAGE = "message";
		private static final String COLUMN_NAME_TIME = "time";

		private SQLiteDatabase mSQLiteDatabase;

		private String id;

		MessageHelper(Context context) {
			super(context, DATABASE_NAME, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + " (" 
					+ COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " 
					+ COLUMN_NAME_ID_SEND + " INTEGER, " 
					+ COLUMN_NAME_ID_RECEIVE + " INTEGER, " 
					+ COLUMN_NAME_MESSAGE + " TEXT, "
					+ COLUMN_NAME_TIME + " TIMESTAMP" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (newVersion > oldVersion) {
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
				onCreate(db);
			}
		}

	}

	class PathContext extends ContextWrapper {
		private String mDirectionPath;

		public PathContext(Context base, String directionPath) {
			super(base);
			mDirectionPath = directionPath;
		}

		@Override
		public SQLiteDatabase openOrCreateDatabase(String name, int mode,
				CursorFactory factory) {
			return super.openOrCreateDatabase(getDatabasePath(name)
					.getAbsolutePath(), mode, factory);
		}

		@Override
		public SQLiteDatabase openOrCreateDatabase(String name, int mode,
				CursorFactory factory, DatabaseErrorHandler errorHandler) {
			return super.openOrCreateDatabase(getDatabasePath(name)
					.getAbsolutePath(), mode, factory, errorHandler);
		}

		@Override
		public File getDatabasePath(String name) {
			File file = new File(mDirectionPath + File.separator + name);
			return file;
		}

	}

}
