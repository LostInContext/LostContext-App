package com.lostincontext.playlists;


import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.commons.list.EmptyListCallback;
import com.lostincontext.commons.list.StatefulAdapter;
import com.lostincontext.data.Playlist;

import java.util.List;

public class PlaylistsAdapter extends StatefulAdapter {


    private List<Playlist> playlists;

    private EmptyListCallback emptyListCallback;

    public PlaylistsAdapter(EmptyListCallback emptyListCallback) {
        super(ContentState.LOADING);
        this.emptyListCallback = emptyListCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, @IdRes int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case R.id.view_type_standard:
                View view = layoutInflater.inflate(R.layout.item_playlist, parent, false);
                return new PlaylistViewHolder(view);

            case R.id.view_type_loading:
                return buildLoadingViewHolder(layoutInflater, parent);

            case R.id.view_type_error:
                return buildErrorViewHolder(layoutInflater, parent, emptyListCallback);

            case R.id.view_type_empty:
                return buildEmptyViewHolder(layoutInflater, parent);

            default:
                throw new IllegalStateException("the adapter is in an invalid state");
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case R.id.view_type_standard:


            case R.id.view_type_loading:
            case R.id.view_type_error: //todo
            case R.id.view_type_empty:
                // nothing to do for these
                break;

            default:
                throw new IllegalStateException("the adapter is in an invalid state");
        }

    }


    @Override public int getContentItemsCount() {
        return playlists.size();
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
}
