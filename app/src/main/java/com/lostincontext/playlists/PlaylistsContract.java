package com.lostincontext.playlists;

import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;
import com.lostincontext.commons.list.EmptyListCallback;
import com.lostincontext.data.playlist.Playlist;

import java.util.List;


public class PlaylistsContract {


    interface View extends BaseView<PlaylistsContract.Presenter> {

        void setPlaylists(List<Playlist> playlists);

    }

    interface Presenter extends BasePresenter, EmptyListCallback {

        @Override void start();

    }
}
