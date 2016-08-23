package com.lostincontext.commons.images


import com.lostincontext.commons.images.DeezerImageUrlGenerator.DeezerImageType

interface DeezerImage {

    val coverMd5: String

    @DeezerImageType val imageType: Long

}
