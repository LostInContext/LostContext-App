package com.lostincontext.playlists

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PlaylistsPresenterModule(private val view: PlaylistsContract.View,
                               private val userId: Long) {

    @Provides
    internal fun provideMainScreenContractView(): PlaylistsContract.View {
        return view
    }

    @Provides
    @Named("userId")
    internal fun provideUserId() : Long {
        return userId
    }

}
