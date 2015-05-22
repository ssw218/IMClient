package com.lyk.imclient.ui.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Locale;

import com.lyk.imclient.R;
import com.lyk.imclient.ui.dialog.ImageDialog;
import com.lyk.imclient.util.SDCardManager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class PanelFragment extends Fragment {
	
	private ImageDialog mImageDialog;
	
	private ImageButton mCameraButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_panel, null);
		mCameraButton = (ImageButton) view.findViewById(R.id.imagebutton_fragment_panel_camera);
		mCameraButton.setOnClickListener(mCameraButtonListener);
		return view;
	}
	
	private static final int REQUEST_CODE_CAMERA = 10;
	String picture_path = null;
	
	private OnClickListener mCameraButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			SDCardManager sdcard = new SDCardManager();
			picture_path = sdcard.getFilePath(SDCardManager.FILE_PICTURE);
			String name = DateFormat.format("yyyyMMddhhmmss", Calendar.getInstance(Locale.CHINA)) + ".png";
			picture_path += "/" + name;
			File file = new File(picture_path);
			Uri uri = Uri.fromFile(file);
			
			intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			
			startActivityForResult(intent, REQUEST_CODE_CAMERA);
		}
		
	};
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CAMERA) {
			if (resultCode == Activity.RESULT_OK) {
				Bitmap bitmap = null;
				Uri uri = null;
				try {
					uri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), picture_path, null, null));
					bitmap = BitmapFactory.decodeFile(picture_path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				// start dialog
				if (mImageDialog == null)
					mImageDialog = new ImageDialog(getActivity());
				mImageDialog.setImageBitmap(bitmap);
				mImageDialog.setImagePath(picture_path);
				mImageDialog.show();
			}
		}
		
		
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	
}
