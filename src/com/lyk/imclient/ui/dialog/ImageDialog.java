package com.lyk.imclient.ui.dialog;

import com.lyk.imclient.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class ImageDialog extends Dialog {
	
	private ImageView mImageView;

	public ImageDialog(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_picture, null);
		mImageView = (ImageView) view.findViewById(R.id.imageview_dialog_picture_image);
		setContentView(view);
	}
	
	public void setImageBitmap(Bitmap bitmap) {
		mImageView.setImageBitmap(bitmap);
	}
	
}
