package com.lostincontext.data.playlist.repo


import android.content.res.Resources
import com.lostincontext.R
import com.lostincontext.data.playlist.DataPlaylist
import com.lostincontext.data.playlist.Playlist
import javax.inject.Inject

class PlaylistsRepository
@Inject constructor(private val resources: Resources) {

    interface Callback {
        fun onPlaylistsLoaded(playlists: List<Playlist>)
        fun onPlaylistsLoadFailed()
    }


    fun getPlaylists(callback: PlaylistsRepository.Callback) {
        val data = resources.openRawResource(R.raw.playlists)
        val playlists = DataPlaylist.deserialize(data)
        callback.onPlaylistsLoaded(playlists)
    }
}
      