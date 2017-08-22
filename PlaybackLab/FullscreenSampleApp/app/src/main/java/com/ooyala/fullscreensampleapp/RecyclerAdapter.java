package com.ooyala.fullscreensampleapp;

import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ooyala.android.OoyalaPlayer;
import com.ooyala.android.PlayerDomain;
import com.ooyala.android.skin.OoyalaSkinLayout;
import com.ooyala.android.skin.OoyalaSkinLayoutController;
import com.ooyala.android.skin.configuration.SkinOptions;
import com.ooyala.android.skin.fullscreenutils.RecyclerViewFullScreenManager;

import java.util.List;

/**
 * TODO: Add brief for RecyclerAdapter.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PlayerHolder> {

	private Application app;
	private List<String> embedCodes;
	private RecyclerViewFullScreenManager recyclerFullScreenManager;
	private RecyclerView recyclerView;
	private SparseArray<OoyalaSkinLayout> skinsMap;


	public RecyclerAdapter(List<String> embedCodes, Application app, RecyclerViewFullScreenManager manager) {
		this.embedCodes = embedCodes;
		this.app = app;

		skinsMap = new SparseArray<>();

		recyclerFullScreenManager = manager;
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);

		this.recyclerView = recyclerView;
	}

	@Override
	public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_row, parent, false);
		return new PlayerHolder(view);
	}

	@Override
	public void onBindViewHolder(PlayerHolder holder, int position) {
		String embedCode = embedCodes.get(position);
		holder.bindPlayer(embedCode, app);
	}

	@Override
	public int getItemCount() {
		return embedCodes.size();
	}

	public void changeSkinsParent(OoyalaSkinLayout expandedPlayer, boolean isAttach){
		LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
		int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
		int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
		for (int i = firstVisiblePosition; i <= lastVisiblePosition; ++i){
			PlayerHolder holder = (PlayerHolder) recyclerView.findViewHolderForAdapterPosition(i);
			if (expandedPlayer != null && !expandedPlayer.equals(holder.playerLayout)) {
				if (isAttach){
					OoyalaSkinLayout layout = skinsMap.get(i);
					if (holder.playerParent != null){
						holder.playerParent.addView(layout);
						holder.playerLayout = layout;
					}
				} else {
					skinsMap.put(i, holder.playerLayout);
					ViewGroup parent = (ViewGroup) holder.playerLayout.getParent();
					if (parent != null) {
						parent.removeView(holder.playerLayout);
					}
				}
			} else {
				Log.d("RecyclerAdapter", "Holders layout are equals");
			}
		}
	}

	public class PlayerHolder extends RecyclerView.ViewHolder {
		private TextView textView;
		private OoyalaSkinLayout playerLayout;
		private ViewGroup playerParent;

		public PlayerHolder(View itemView) {
			super(itemView);

			textView = (TextView) itemView.findViewById(R.id.textView);
			playerParent = (ViewGroup) itemView.findViewById(R.id.skinParentContainer);
			playerLayout = (OoyalaSkinLayout) itemView.findViewById(R.id.ooyalaSkinLayout);
			playerLayout.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
				@Override
				public void onSystemUiVisibilityChange(int visibility) {
					boolean isFullScreenMode = playerLayout.isFullscreen();
					if (isFullScreenMode) {
						changeSkinsParent(playerLayout, false);
						recyclerFullScreenManager.expandPlayerLayout(playerLayout);
					} else {
						changeSkinsParent(playerLayout, true);
						recyclerFullScreenManager.collapsePlayerLayout();
					}
				}
			});
		}

		public void bindPlayer(String embedCode, Application app) {
			textView.setText("Sample text");

			OoyalaPlayer player = new OoyalaPlayer("c0cTkxOqALQviQIGAHWY5hP0q9gU", new PlayerDomain("http://www.ooyala.com/"));

			SkinOptions options = new SkinOptions.Builder().build();
			OoyalaSkinLayoutController playerController = new OoyalaSkinLayoutController(app, playerLayout, player, options);
			player.setEmbedCode(embedCode);
		}
	}
}
