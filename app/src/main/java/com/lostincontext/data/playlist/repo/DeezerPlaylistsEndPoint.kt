package com.lostincontext.data.playlist.repo

import com.lostincontext.data.playlist.PlaylistsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface DeezerPlaylistsEndPoint {

    @GET("user/{user}/playlists")
    fun getUserPlaylists(@Path("user") query: Long): Call<PlaylistsData>


}
