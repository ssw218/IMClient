package com.lyk.imclient.ui.view;

import com.lyk.imclient.R;
import com.lyk.imclient.ui.dialog.EnlargeImageDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImageSendView extends RelativeLayout {
	private ImageView mHostPhoto;
	private ImageView mSendImage;
	private Bitmap mBitmap;
	private EnlargeImageDialog mDialog;
	
	private OnClickListener mImageShow = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mDialog.show();
		}
		
	};
	
	public ImageSendView(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.image_send_view, null);
		mHostPhoto = (ImageView) view.findViewById(R.id.imageview_image_send_view_photo);
		mSendImage = (ImageView) view.findViewById(R.id.imageview_image_send_view_image);
		mSendImage.setOnClickListener(mImageShow);
		addView(view);
		
		mDialog = new EnlargeImageDialog(context);
	}

	public void setHostPhoto(Bitmap bitmap) {
		mHostPhoto.setImageBitmap(bitmap);
	}
	
	public void setSendImage(Bitmap bitmap) {
		mBitmap = bitmap;
		mSendImage.setImageBitmap(bitmap);
		mDialog.setImageBitmap(bitmap);
	}
	
	
	
}
