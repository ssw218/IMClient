package com.lyk.imclient.ui.dialog;

import com.lyk.imclient.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ImageDialog extends Dialog {
	
	private ImageView mImageView;

	public ImageDialog(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_picture, null);
		mImageView = (ImageView) view.findViewById(R.id.imageview_dialog_picture_image);
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		LayoutParams lp = new LayoutParams(width, height);
		setContentView(view, lp);
	}
	
	public void setImageBitmap(Bitmap bitmap) {
		mImageView.setImageBitmap(bitmap);
	}
	
	
	
}
