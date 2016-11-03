package com.lostincontext.data.playlist

import com.squareup.moshi.Json


data class PlaylistsData(@Json(name = "data") val playlists: List<Playlist>?,
                         val total: Int,
                         val next: String)