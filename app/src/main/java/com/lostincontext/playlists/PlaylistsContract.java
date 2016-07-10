package com.lostincontext.playlists;

import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;
import com.lostincontext.commons.list.EmptyListCallback;


public class PlaylistsContract {


    interface View extends BaseView<PlaylistsContract.Presenter> {

    }

    interface Presenter extends BasePresenter, EmptyListCallback {

        @Override void start();

    }
}
