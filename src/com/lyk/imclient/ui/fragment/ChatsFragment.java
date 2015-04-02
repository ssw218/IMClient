package com.lyk.imclient.ui.fragment;

import java.util.ArrayList;

import com.lyk.imclient.R;
import com.lyk.imclient.ui.adapter.ChatsAdapter;
import com.lyk.imclient.ui.view.ChatSimpleView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChatsFragment extends Fragment {
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private ArrayList<ChatSimpleView> mList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_tab_chats, container, false);
		mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
	    mRecyclerView.setHasFixedSize(true);
	    mLayoutManager = new LinearLayoutManager(getActivity());
	    mRecyclerView.setLayoutManager(mLayoutManager);
	    
	    mList = new ArrayList<ChatSimpleView>();
	    mList.add(new ChatSimpleView(getActivity()));
	    mList.add(new ChatSimpleView(getActivity()));
	    mList.add(new ChatSimpleView(getActivity()));
	    mList.add(new ChatSimpleView(getActivity()));
	    mList.add(new ChatSimpleView(getActivity()));
	    mList.add(new ChatSimpleView(getActivity()));
	    mList.add(new ChatSimpleView(getActivity()));
	    mList.add(new ChatSimpleView(getActivity()));
	    mList.add(new ChatSimpleView(getActivity()));
	    mList.add(new ChatSimpleView(getActivity()));
	    mList.add(new ChatSimpleView(getActivity()));
	    mList.add(new ChatSimpleView(getActivity()));
	    mAdapter = new ChatsAdapter(mList);
	    mRecyclerView.setAdapter(mAdapter);
		
		return view;
	}
	
	
}
