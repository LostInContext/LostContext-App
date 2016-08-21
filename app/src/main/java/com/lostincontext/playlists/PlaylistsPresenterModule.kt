package com.lostincontext.playlists

import dagger.Module
import dagger.Provides

@Module
class PlaylistsPresenterModule(private val view: PlaylistsContract.View) {

    @Provides
    internal fun provideMainScreenContractView(): PlaylistsContract.View {
        return view
    }

}
