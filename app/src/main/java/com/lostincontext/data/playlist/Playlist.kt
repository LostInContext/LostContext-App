package com.lostincontext.data.playlist


import com.lostincontext.commons.images.DeezerImage
import com.lostincontext.commons.images.DeezerImageUrlGenerator.DeezerImageType
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable

@PaperParcel
data class Playlist(val id: Long,
                    val title: String,
                    val author: Author,
                    override val coverMd5: String,
                    override @DeezerImageType val imageType: Long)
    : DeezerImage, PaperParcelable {


//region Parcelable

    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(Playlist::class.java)
    }
//endregion


}