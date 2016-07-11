package com.lostincontext.playlists;


import com.lostincontext.data.DataPlaylist;
import com.lostincontext.data.Playlist;

import java.util.List;

import javax.inject.Inject;

public class PlaylistsPresenter implements PlaylistsContract.Presenter {

    private PlaylistsContract.View view;


    @Inject PlaylistsPresenter(PlaylistsContract.View view) {
        this.view = view;
    }

    @Inject void setup() {
        view.setPresenter(this);
    }

    @Override public void start() {
        List<Playlist>  playlists = DataPlaylist.getPlaylists();
        view.setPlaylists(playlists);
    }

    @Override public void onRefreshButtonClick() {

    }
}
