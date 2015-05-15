package com.lyk.imclient.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class SDCardManager {
	private static final String TAG = "SDCardManager";
	private static final boolean DEBUG = false;

	private static final String FILE_FATHER = "IMClient";

	public static final String FILE_LOGIN = "login";
	public static final String FILE_PHOTO = "photo";
	public static final String FILE_PICTURE = "picture";
	public static final String FILE_THUMB = "thumb";
	public static final String FILE_IMAGE = "image";
	public static final String FILE_AUDIO = "audio";

	private String mPath;
//	private String mId;

	public SDCardManager() {
		mPath = Environment.getExternalStorageDirectory() + "/" + FILE_FATHER;
		if (DEBUG)
			Log.v(TAG, "sdcard path : " + mPath);
//		mId = id;
		File directory = new File(mPath);
		if (DEBUG)
			Log.v(TAG, "exists : " + directory.exists());
		if (!directory.exists()) {
			try {
				directory.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (DEBUG)
			Log.v(TAG, "exists : " + directory.exists());
//		File db = new File(mPath + "/" + mId);
//		if (!db.exists())
//			db.mkdirs();
		File login = new File(mPath + "/" + FILE_LOGIN);
		if (!login.exists())
			login.mkdirs();
		File photo = new File(mPath + "/" + FILE_PHOTO);
		if (!photo.exists())
			photo.mkdirs();
		File picture = new File(mPath + "/" + FILE_PICTURE);
		if (!picture.exists())
			picture.mkdirs();
		File thumb = new File(mPath + "/" + FILE_THUMB);
		if (!thumb.exists())
			thumb.mkdirs();
		File image = new File(mPath + "/" + FILE_IMAGE);
		if (!image.exists())
			image.mkdirs();
		File audio = new File(mPath + "/" + FILE_AUDIO);
		if (!audio.exists())
			audio.mkdirs();
	}
	
	public String getFilePath(String file) {
		return mPath + "/" + file;
	}
	
	public Drawable getImageFromSDCard(String file, String name, Rect rect, Resources resources) {
		Bitmap bitmap = BitmapFactory.decodeFile(getImagePath(file, name));
		bitmap = Bitmap.createScaledBitmap(bitmap, rect.width(), rect.height(), true);
		Drawable drawable = new BitmapDrawable(resources, bitmap);
		return drawable;
	}
	
//	public String getDatabaseParentPath() {
//		return mPath + "/" + mId;
//	}

	private String getImagePath(String file, String name) {
		return mPath + "/" + file + "/" + name;
	}

	public boolean exists(String file, String name) {
		File f = new File(getImagePath(file, name));
		return f.exists();
	}

	public Bitmap getBitmap(String file, String name) {
		Bitmap bitmap = null;
		if (exists(file, name))
			bitmap = BitmapFactory.decodeFile(getImagePath(file, name));
		return  bitmap;
	}
	
	public void saveImage(byte[] imageByte, String file, String name) {
		Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0,
				imageByte.length);
		File f = new File(getImagePath(file, name));
		if (f.exists())
			f.delete();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f);
			image.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
