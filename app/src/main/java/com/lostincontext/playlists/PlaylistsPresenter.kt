package com.lostincontext.playlists


import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.playlist.repo.PlaylistsRepository
import javax.inject.Inject

class PlaylistsPresenter
@Inject internal constructor(private val view: PlaylistsContract.View,
                             private val playlistsRepository: PlaylistsRepository) : PlaylistsContract.Presenter, PlaylistsRepository.Callback {


    @Inject internal fun setup() {
        view.setPresenter(this)
    }

    override fun start() {
        playlistsRepository.getPlaylists(this)
    }

    override fun onRefreshButtonClick() {
    }

    override fun onDeezerLogoClick(playlist: Playlist) {
        view.openDeezerFor(playlist)
    }

    override fun onItemClick(playlist: Playlist) {
        view.returnResult(playlist)
    }


    override fun onPlaylistsLoaded(playlists: List<Playlist>) {
        view.setPlaylists(playlists)
    }

    override fun onPlaylistsLoadFailed() {
        throw UnsupportedOperationException("not implemented")
    }
}
