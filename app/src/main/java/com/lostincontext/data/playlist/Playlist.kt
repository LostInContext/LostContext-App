package com.lostincontext.data.playlist


import android.os.Parcel
import android.os.Parcelable
import com.lostincontext.commons.images.DeezerImage
import com.lostincontext.commons.images.DeezerImageUrlGenerator.DeezerImageType

data class Playlist(val id: Int,
                    val title: String,
                    val creator: String,
                    override val coverMd5: String,
                    override @DeezerImageType val imageType: Long) : DeezerImage, Parcelable {


    private constructor(`in`: Parcel) : this(`in`.readInt(),
                                             `in`.readString(),
                                             `in`.readString(),
                                             `in`.readString(),
                                             `in`.readLong())

//region Parcelable

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeInt(id)
        out.writeString(title)
        out.writeString(creator)
        out.writeString(coverMd5)
        out.writeLong(imageType)
    }

    companion object {

        @JvmField val CREATOR = object : Parcelable.Creator<Playlist> {
            override fun createFromParcel(`in`: Parcel): Playlist {
                return Playlist(`in`)
            }

            override fun newArray(size: Int): Array<Playlist?> {
                return arrayOfNulls(size)
            }
        }
    }
//endregion


}