package com.lostincontext.playlists


import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.playlist.repo.PlaylistsRepository
import com.lostincontext.playlists.PlaylistsContract.NO_USER
import javax.inject.Inject
import javax.inject.Named

class PlaylistsPresenter
@Inject internal constructor(private val view: PlaylistsContract.View,
                             private @Named("userId") val userId: Long,
                             private val playlistsRepository: PlaylistsRepository) : PlaylistsContract.Presenter,
                                                                                     PlaylistsRepository.Callback {


    override fun start() {
        when (userId) {
            NO_USER -> playlistsRepository.getHardcodedPlaylists(this)
            else -> queryPlaylists()
        }

    }

    private fun queryPlaylists() {
        playlistsRepository.queryPlaylists(userId,
                                           {
                                               if (it.playlists != null) {
                                                   view.setPlaylists(it.playlists)
                                               } else {
                                                   view.setPlaylists(emptyList())
                                               }


                                           }, {

                                           })
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
