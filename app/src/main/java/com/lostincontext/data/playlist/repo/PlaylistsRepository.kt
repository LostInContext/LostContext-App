package com.lostincontext.data.playlist.repo


import android.content.res.Resources
import com.lostincontext.R
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.playlist.PlaylistsData
import com.lostincontext.utils.enqueue
import com.squareup.moshi.Moshi
import okio.Okio
import java.io.InputStream
import javax.inject.Inject

class PlaylistsRepository
@Inject constructor(val playlistsEndPoint: DeezerPlaylistsEndPoint,
                    private val resources: Resources,
                    private val moshi: Moshi) {


    inline fun queryPlaylists(userId: Long,
                              crossinline success: (playlistsData: PlaylistsData) -> Unit,
                              crossinline failure: (t: Int) -> Unit) {
        playlistsEndPoint.getUserPlaylists(userId).enqueue(success,
                                                           failure)
    }

    inline fun queryMorePlaylists(next: String,
                                  crossinline success: (playlistsData: PlaylistsData) -> Unit,
                                  crossinline failure: (t: Int) -> Unit) {
        playlistsEndPoint.getUserPlaylistsPaginate(next).enqueue(success,
                                                                 failure)
    }


    fun getHardcodedPlaylists(success: (playlistsData: PlaylistsData) -> Unit) {
        val data = resources.openRawResource(R.raw.playlists)
        val playlistDataAdapter = moshi.adapter(PlaylistsData::class.java)
        val deezerData = playlistDataAdapter.fromJson(data.toBufferedSource())
        success.invoke(deezerData)
    }

    private fun InputStream.toBufferedSource() = Okio.buffer(Okio.source(this))
}
      