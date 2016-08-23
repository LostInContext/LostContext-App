package com.lostincontext.commons.images

import android.support.annotation.IntDef
import android.support.annotation.StringDef
import android.text.TextUtils.isEmpty
import com.bumptech.glide.request.target.Target
import kotlin.annotation.AnnotationRetention.SOURCE


object DeezerImageUrlGenerator {


    @Retention(SOURCE)
    @IntDef(TYPE_COVER,
            TYPE_ARTIST,
            TYPE_USER,
            TYPE_PLAYLIST_CUSTOM_COVER,
            TYPE_MISC)
    annotation class DeezerImageType

    const val TYPE_COVER = 0L
    const val TYPE_ARTIST = 1L
    const val TYPE_USER = 2L
    /**
     * **CAUTION !** This is the type to use for the full cover set by the users.
     * However, Playlists can also use a patchwork of album covers instead.
     * Use `Playlist.getImageType()` in order to determine which type you need to use.
     */
    const val TYPE_PLAYLIST_CUSTOM_COVER = 3L
    const val TYPE_MISC = 4L

    @Retention(SOURCE)
    @StringDef(SUB_PATH_COVER,
               SUB_PATH_ARTIST,
               SUB_PATH_USER,
               SUB_PATH_PLAYLIST,
               SUB_PATH_MISC)
    annotation class DeezerImageSubPath

    const val SUB_PATH_COVER = "cover"
    const val SUB_PATH_ARTIST = "artist"
    const val SUB_PATH_USER = "user"
    const val SUB_PATH_PLAYLIST = "playlist"
    const val SUB_PATH_MISC = "misc"

    /**
     * image spec : background color - compression - mode - version .jpg
     */
    val IMAGE_SPEC_JPG = "-000000-80-0-0.jpg"

    @SuppressWarnings("MagicNumber")
    private val IMAGE_BUCKETS = intArrayOf(60, 120, 200, 340, 400, 500, 720)


    private val imageUrlPrefix = "http://cdn-images.deezer.com/images/"


    fun buildUrl(deezerImage: DeezerImage?,
                 width: Int,
                 height: Int): String? {

        if (deezerImage == null) return null
        val coverMd5 = deezerImage.coverMd5
        if (isEmpty(coverMd5)) return null

        @DeezerImageSubPath val subPath: String
        when (deezerImage.imageType) {
            TYPE_COVER -> subPath = SUB_PATH_COVER

            TYPE_ARTIST -> subPath = SUB_PATH_ARTIST

            TYPE_PLAYLIST_CUSTOM_COVER -> subPath = SUB_PATH_PLAYLIST

            TYPE_USER -> subPath = SUB_PATH_USER

            TYPE_MISC -> subPath = SUB_PATH_MISC

            else -> subPath = SUB_PATH_MISC
        }

        val size = getSquareImageBucket(width, height)
        return "$imageUrlPrefix$subPath/$coverMd5/${size}x$size$IMAGE_SPEC_JPG"
    }


    private fun getSquareImageBucket(width: Int,
                                     height: Int): Int {
        if (width == Target.SIZE_ORIGINAL) {
            // we provide the biggest bucket for this configuration :
            return IMAGE_BUCKETS[IMAGE_BUCKETS.size - 1]
        }

        val maxSide = Math.max(width, height)

        for (bucket in IMAGE_BUCKETS) {
            if (bucket >= maxSide) {
                return bucket
            }
        }
        return IMAGE_BUCKETS[IMAGE_BUCKETS.size - 1]
    }


}
