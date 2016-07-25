package com.lostincontext.playlists;

import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;
import com.lostincontext.commons.list.EmptyListCallback;
import com.lostincontext.data.playlist.Playlist;

import java.util.List;


public class PlaylistsContract {


    public static final String EXTRA_PLAYLIST = "playlist";

    interface View extends BaseView<PlaylistsContract.Presenter> {

        void setPlaylists(List<Playlist> playlists);

        void openDeezerFor(Playlist playlist);

        void returnResult(Playlist playlist);
    }

    interface Presenter extends BasePresenter,
                                EmptyListCallback,
                                PlaylistViewHolder.Callback {

        @Override void start();

    }
}
