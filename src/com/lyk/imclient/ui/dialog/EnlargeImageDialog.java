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

public class EnlargeImageDialog extends Dialog {
	
	private ImageView mImageView;
	
	public EnlargeImageDialog(Context context) {
		super(context, R.style.Dialog);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_enlarge_image, null);
		mImageView = (ImageView) view.findViewById(R.id.imageview_dialog_enlarge_image_image);
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		int width = displayMetrics.widthPixels;
		LayoutParams lp = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
		setContentView(view, lp);
	}

	public void setImageBitmap(Bitmap bitmap) {
		mImageView.setImageBitmap(bitmap);
		
		
	}
	
}
