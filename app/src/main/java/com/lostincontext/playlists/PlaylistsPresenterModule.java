package com.lostincontext.playlists;

import dagger.Module;
import dagger.Provides;

@Module
public class PlaylistsPresenterModule {

    private final PlaylistsContract.View view;

    public PlaylistsPresenterModule(PlaylistsContract.View view) {
        this.view = view;
    }

    @Provides
    PlaylistsContract.View provideMainScreenContractView() {
        return view;
    }

}
