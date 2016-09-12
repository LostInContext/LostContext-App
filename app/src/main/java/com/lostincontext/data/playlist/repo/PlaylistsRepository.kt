package com.lostincontext.data.playlist.repo


import android.content.res.Resources
import com.lostincontext.R
import com.lostincontext.data.playlist.DataPlaylist
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.playlist.PlaylistsData
import com.lostincontext.data.user.repo.DeezerPlaylistsEndPoint
import com.lostincontext.utils.enqueue
import retrofit2.Response
import javax.inject.Inject

class PlaylistsRepository
@Inject constructor(private val resources: Resources,
                    val playlistsEndPoint: DeezerPlaylistsEndPoint) {

    interface Callback {
        fun onPlaylistsLoaded(playlists: List<Playlist>)
        fun onPlaylistsLoadFailed()
    }


    inline fun queryPlaylists(userId: Long,
                              crossinline success: (response: Response<PlaylistsData>) -> Unit,
                              crossinline failure: (t: Throwable) -> Unit) {
        playlistsEndPoint.getUserPlaylists(userId).enqueue(success,
                                                           failure)
    }

    fun getHardcodedPlaylists(callback: PlaylistsRepository.Callback) {
        val data = resources.openRawResource(R.raw.playlists)
        val playlists = DataPlaylist.deserialize(data)
        callback.onPlaylistsLoaded(playlists)
    }
}
      