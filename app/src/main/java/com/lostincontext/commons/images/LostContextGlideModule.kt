package com.lostincontext.commons.images


import android.content.Context

import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.module.GlideModule

import java.io.InputStream

class LostContextGlideModule : GlideModule {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // Do nothing.
    }


    override fun registerComponents(context: Context, glide: Glide) {
        glide.register(DeezerImage::class.java,
                       InputStream::class.java,
                       DeezerImageLoader.Factory())
    }


}