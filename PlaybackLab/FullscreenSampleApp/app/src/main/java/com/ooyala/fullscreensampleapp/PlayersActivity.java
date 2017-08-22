package com.ooyala.fullscreensampleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class PlayersActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragmentByTag = fragmentManager.findFragmentByTag(RecyclerFragment.TAG);
		if (fragmentByTag == null){
			RecyclerFragment recyclerFragment = new RecyclerFragment();
			fragmentManager.beginTransaction().replace(R.id.container, recyclerFragment, RecyclerFragment.TAG).commit();
		}
	}

	@Override
	public void onBackPressed() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager.getBackStackEntryCount() > 0){
			fragmentManager.popBackStack();
		} else {
			super.onBackPressed();
		}
	}
}
