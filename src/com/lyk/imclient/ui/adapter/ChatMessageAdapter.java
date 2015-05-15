package com.lyk.imclient.ui.adapter;

import java.util.ArrayList;

import com.lyk.imclient.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {
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
		ViewHolder vh = new ViewHolder(LayoutInflater.from((parent.getContext())).inflate(R.layout.chat_send_view, null));
		return vh;
	}
}
