package com.lostincontext.playlists;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.model.Playlist;

import java.util.List;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.PlaylistViewHolder> {


    private List<Playlist> playlists;

    public PlaylistsAdapter(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PlaylistViewHolder holder, int position) {

    }

    @Override public int getItemCount() {
        return playlists.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {

        public PlaylistViewHolder(View itemView) {
            super(itemView);
        }
    }
}
