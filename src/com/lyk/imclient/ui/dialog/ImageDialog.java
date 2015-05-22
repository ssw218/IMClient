package com.lyk.imclient.ui.dialog;

import java.io.File;

import com.lyk.imclient.R;
import com.lyk.imclient.activity.ChatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;

public class ImageDialog extends Dialog {
	
	private Context mContext;
	
	private ImageView mImageView;
	private Button mBackButton;
	private Button mSendButton;
	private String mImagePath;
	
	private Bitmap mImageBitmap;
	
	private View.OnClickListener mBackButtonListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			hide();
			File file = new File(mImagePath);
			file.delete();
		}

		
	};
	
	private View.OnClickListener mSendButtonListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Message message = new Message();
			message.what = ChatActivity.ActivityHandler.MESSAGE_SEND_IMAGE;
			message.obj = mImageBitmap;
			((ChatActivity) mContext).getHandler().sendMessage(message);
			hide();
		}
	};

	public ImageDialog(Context context) {
		super(context, R.style.Dialog);
		mContext = context;
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_picture, null);
		mImageView = (ImageView) view.findViewById(R.id.imageview_dialog_picture_image);
		mBackButton = (Button) view.findViewById(R.id.button_dialog_picture_back);
		mSendButton = (Button) view.findViewById(R.id.button_dialog_picture_send);
		
		mBackButton.setOnClickListener(mBackButtonListener);
		mSendButton.setOnClickListener(mSendButtonListener);
		
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		LayoutParams lp = new LayoutParams(width, height);
		setContentView(view, lp);
	}
	
	public void setImageBitmap(Bitmap bitmap) {
		mImageBitmap = bitmap;
		mImageView.setImageBitmap(bitmap);
	}

	public void setImagePath(String path) {
		mImagePath = path;
	}
	
	
	
}
