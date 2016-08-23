package com.lostincontext.playlists;


import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.commons.list.EmptyListCallback;
import com.lostincontext.commons.list.StatefulAdapter;
import com.lostincontext.commons.list.ViewHolder;
import com.lostincontext.data.playlist.Playlist;

import java.util.List;

import static java.util.Collections.emptyList;

public class PlaylistsAdapter extends StatefulAdapter {

    private List<Playlist> playlists = emptyList();

    private PlaylistViewHolder.Callback itemCallback;
    private EmptyListCallback emptyListCallback;

    public PlaylistsAdapter(PlaylistViewHolder.Callback itemCallback,
                            EmptyListCallback emptyListCallback) {
        super(ContentState.LOADING);
        this.itemCallback = itemCallback;
        this.emptyListCallback = emptyListCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, @IdRes int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case R.id.view_type_standard:
                return PlaylistViewHolder.create(layoutInflater, parent, itemCallback);

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

    @Override public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        switch (holder.getItemViewType()) {
            case R.id.view_type_standard:
                PlaylistViewHolder viewHolder = (PlaylistViewHolder) holder;
                viewHolder.bindTo(playlists.get(position));
                break;


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
        if (playlists.size() == 0) setCurrentState(ContentState.EMPTY);
        else setCurrentState(ContentState.CONTENT);
    }
}
