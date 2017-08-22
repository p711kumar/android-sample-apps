package com.ooyala.fullscreensampleapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ooyala.android.skin.fullscreenutils.RecyclerViewFullScreenManager;

import java.util.ArrayList;
import java.util.List;

public class RecyclerFragment extends Fragment {

	public static final String TAG = RecyclerFragment.class.getName();

	private FrameLayout expandedLayout;
	private RecyclerView recyclerView;
	private RecyclerViewFullScreenManager screenManager;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View inflated = inflater.inflate(R.layout.recycler_fragment, container, false);
		expandedLayout = (FrameLayout) inflated.findViewById(R.id.empty_view);
		recyclerView = (RecyclerView) inflated.findViewById(R.id.recycler_view);

		setupRecycler();

		return inflated;
	}


	private void setupRecycler(){
		screenManager = new RecyclerViewFullScreenManager(expandedLayout);

		List<String> embedCodes = new ArrayList<>();
		embedCodes.add("JiOTdrdzqAujYa5qvnOxszbrTEuU5HMt");
		embedCodes.add("h4aHB1ZDqV7hbmLEv4xSOx3FdUUuephx");
		embedCodes.add("42cnNsMjE62UDH0JlCssXEPhxlhj1YBN");
		embedCodes.add("E5NWlqMzE6nxrKShm0gR4DzpM49Wl0l9");
		embedCodes.add("JiOTdrdzqAujYa5qvnOxszbrTEuU5HMt");
		screenManager = new RecyclerViewFullScreenManager(expandedLayout);
		RecyclerAdapter recyclerAdapter = new RecyclerAdapter(embedCodes, getActivity().getApplication(), screenManager);

		recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
		recyclerView.setAdapter(recyclerAdapter);
		recyclerView.setHasFixedSize(true);
	}

}
