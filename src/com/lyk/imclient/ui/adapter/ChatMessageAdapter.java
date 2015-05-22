package com.lyk.imclient.ui.adapter;

import java.util.ArrayList;

import com.lyk.imclient.R;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {
	private static final String TAG = "ChatMessageAdapter";
	private static final boolean DEBUG = false;
	private ArrayList<View> mList;
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public View mView;
		public ViewHolder(View view) {
			super(view);
			mView = view;
		}
		
	}

	public ChatMessageAdapter(ArrayList<View> list) {
		mList = list;
	}
	
	@Override
	public int getItemCount() {
		return mList.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (DEBUG) Log.e(TAG, "onCreateViewHolder count : " + getItemCount());
		ViewHolder vh = null;
		if (getItemCount() > 0)
			vh = new ViewHolder(mList.get(getItemCount() - 1));
		else
			vh = new ViewHolder(new TextView(parent.getContext()));
		return vh;
	}
}
