package com.lostincontext.data.user

import com.lostincontext.commons.images.DeezerImage
import com.lostincontext.commons.images.DeezerImageUrlGenerator
import com.squareup.moshi.Json


data class User(val id: Long,
                val name: String,
                override @field:UserImage @Json(name = "picture_medium") val coverMd5: String,
                @Json(name = "tracklist") val flow: String) : DeezerImage {

    override val imageType: Long
        get() = DeezerImageUrlGenerator.TYPE_USER

}


