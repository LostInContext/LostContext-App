package com.lostincontext.playlists;


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
    }

    @Override public void onRefreshButtonClick() {

    }
}
