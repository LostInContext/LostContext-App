package com.lostincontext.playlists


import com.lostincontext.data.playlist.DataPlaylist
import com.lostincontext.data.playlist.Playlist

import javax.inject.Inject

class PlaylistsPresenter
@Inject internal constructor(private val view: PlaylistsContract.View) : PlaylistsContract.Presenter {

    @Inject internal fun setup() {
        view.setPresenter(this)
    }

    override fun start() {
        val playlists = DataPlaylist.playlists
        view.setPlaylists(playlists)
    }

    override fun onRefreshButtonClick() {
    }

    override fun onDeezerLogoClick(playlist: Playlist) {
        view.openDeezerFor(playlist)
    }

    override fun onItemClick(playlist: Playlist) {
        view.returnResult(playlist)
    }
}
