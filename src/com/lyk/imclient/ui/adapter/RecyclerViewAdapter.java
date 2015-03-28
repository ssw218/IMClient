package com.lyk.imclient.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
	private String[] mDataset;
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView mTextView;
		public ViewHolder(TextView view) {
			super(view);
			mTextView = view;
		}
	}
	
	public RecyclerViewAdapter(String[] dataset) {
		mDataset = dataset;
	}
	
	@Override
	public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ViewHolder vh = new ViewHolder(new TextView(parent.getContext()));
		return vh;
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.mTextView.setText(mDataset[position]);
	}
	
	@Override
	public int getItemCount() {
		return mDataset.length;
	}
	
}
