package com.lyk.imclient.ui.adapter;

import java.util.ArrayList;

import com.lyk.imclient.ui.view.ChatSimpleView;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
	private ArrayList<ChatSimpleView> mList;
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ChatSimpleView mChatSimpleView;
		public ViewHolder(ChatSimpleView view) {
			super(view);
			mChatSimpleView = view;
		}
	}
	
	public RecyclerViewAdapter(ArrayList<ChatSimpleView> list) {
		mList = list;
	}
	
	@Override
	public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ViewHolder vh = new ViewHolder(new ChatSimpleView(parent.getContext()));
		return vh;
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		//holder.mChatSimpleView.setText(mDataset[position]);
	}
	
	@Override
	public int getItemCount() {
		return mList.size();
	}
	
}
