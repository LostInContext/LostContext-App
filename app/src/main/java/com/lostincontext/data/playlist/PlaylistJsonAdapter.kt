package com.lostincontext.data.playlist

import android.text.TextUtils
import com.lostincontext.commons.images.DeezerImageUrlGenerator.TYPE_COVER
import com.lostincontext.commons.images.DeezerImageUrlGenerator.TYPE_PLAYLIST_CUSTOM_COVER
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson


class PlaylistJsonAdapter {
    @Suppress("unused")
    @FromJson fun playlistFromJson(playlistJson: PlaylistJson): Playlist {


        val image = playlistJson.picture_small
        val (coverMd5, coverType) = extractCoverInfo(image)

        return Playlist(playlistJson.id,
                        playlistJson.title,
                        playlistJson.creator,
                        coverMd5,
                        coverType)
    }

    private data class DataHolder(val coverMd5: String, val coverType: Long)

    /**
     * dirty hack around the fact that the API can't simply return the coverMd5
     */
    private fun extractCoverInfo(image: String): DataHolder {
        val coverType: Long
        var beginning = image.indexOf(COVER) + COVER.length
        if (beginning == (-1 + COVER.length)) {
            beginning = image.indexOf(PLAYLIST) + PLAYLIST.length
            coverType = TYPE_PLAYLIST_CUSTOM_COVER
        } else {
            coverType = TYPE_COVER
        }
        val end = image.indexOf("/", beginning)
        val coverMd5 = TextUtils.substring(image, beginning, end)
        return DataHolder(coverMd5, coverType)
    }

    @Suppress("unused")
    @ToJson fun playlistToJson(playlist: Playlist): PlaylistJson {

        val prefix: String

        when (playlist.imageType) {
            TYPE_PLAYLIST_CUSTOM_COVER -> prefix = COVER
            TYPE_COVER -> prefix = "/playlist/"
            else -> throw RuntimeException("invalid playlist cover type")
        }

        val encodedCover = "$prefix${playlist.coverMd5}/"

        val json = PlaylistJson(playlist.id,
                                playlist.title,
                                encodedCover,
                                playlist.creator)
        return json
    }

    companion object {
        const val COVER = "/cover/"
        const val PLAYLIST = "/playlist/"
    }
}


