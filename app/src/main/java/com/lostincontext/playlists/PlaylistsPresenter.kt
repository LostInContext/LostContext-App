package com.lostincontext.playlists


import android.text.TextUtils
import com.lostincontext.commons.list.StatefulAdapter.ContentState.ERROR
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.playlist.PlaylistsData
import com.lostincontext.data.playlist.repo.PlaylistsRepository
import com.lostincontext.playlists.PlaylistsContract.NO_USER
import com.lostincontext.utils.logD
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class PlaylistsPresenter
@Inject internal constructor(private val view: PlaylistsContract.View,
                             private @Named("userId") val userId: Long,
                             private val playlistsRepository: PlaylistsRepository) : PlaylistsContract.Presenter {

    private var next: String? = null
    private var playlists: List<Playlist> = ArrayList(25)

    override fun start() {
        when (userId) {
            NO_USER -> queryHardcodedPlaylists()
            else -> queryPlaylists()
        }

    }

    private fun queryPlaylists() {
        playlistsRepository.queryPlaylists(userId,
                                           { displayPlaylists(it) },
                                           { view.setContentState(ERROR) })
    }

    private fun queryHardcodedPlaylists() {
        playlistsRepository.getHardcodedPlaylists() { displayPlaylists(it) }

    }

    private fun displayPlaylists(playlistsData: PlaylistsData) {
        if (playlistsData.playlists != null) {
            playlists = playlistsData.playlists
            next = playlistsData.next
            view.setPlaylists(playlists, playlistsData.total)
        } else {
            view.setPlaylists(emptyList(), playlistsData.total)
        }
    }

    override fun onRefreshButtonClick() {
    }

    override fun onDeezerLogoClick(playlist: Playlist) {
        view.openDeezerFor(playlist)
    }

    override fun onItemClick(playlist: Playlist) {
        view.returnResult(playlist)
    }


    override fun onNeedMoreItems() {
        val next = next ?: return
        logD("test") { "need more items" }
        playlistsRepository.queryMorePlaylists(next,
                                               { displayMoreItems(it) },
                                               { view.setContentState(ERROR) })
    }


    fun displayMoreItems(playlistsData: PlaylistsData) {

        next = playlistsData.next

        val elements = playlistsData.playlists ?: return
        val newPlaylists = ArrayList(playlists)
        newPlaylists.addAll(elements)
        playlists = newPlaylists
        logD("TAG") { "swap : $next, total : ${playlistsData.total} , current size : ${playlists.size}" }
        view.swap(newPlaylists, !TextUtils.isEmpty(next))
    }


    override fun onRetryClick() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
