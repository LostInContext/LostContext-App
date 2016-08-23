package com.lostincontext.data.playlist


import android.os.Parcel
import android.os.Parcelable

import com.lostincontext.commons.images.DeezerImage
import com.lostincontext.commons.images.DeezerImageUrlGenerator.DeezerImageType

class Playlist : DeezerImage, Parcelable {

    var id: Int = 0
    var title: String? = null
    var creator: String? = null
    override var coverMd5: String = ""
    @DeezerImageType override var imageType: Long = 0

    constructor() {
    }

    constructor(id: Int,
                title: String,
                creator: String,
                coverMd5: String,
                @DeezerImageType imageType: Int) {
        this.id = id
        this.title = title
        this.creator = creator
        this.coverMd5 = coverMd5
        this.imageType = imageType.toLong()
    }

    private constructor(`in`: Parcel) {
        this.id = `in`.readInt()
        this.title = `in`.readString()
        this.creator = `in`.readString()
        this.coverMd5 = `in`.readString()
        //noinspection WrongConstant
        this.imageType = `in`.readLong()
    }

    override fun toString(): String {
        return "Playlist{id=$id, title='$title', creator='$creator', coverMd5='$coverMd5', imageType=$imageType}"
    }


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