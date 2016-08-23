package com.lostincontext.commons.images


import android.content.Context

import com.bumptech.glide.load.model.GenericLoaderFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader

import java.io.InputStream

class DeezerImageLoader(urlLoader: ModelLoader<GlideUrl, InputStream>) : BaseGlideUrlLoader<DeezerImage>(
        urlLoader) {


    class Factory : ModelLoaderFactory<DeezerImage, InputStream> {

        override fun build(context: Context,
                           factories: GenericLoaderFactory): ModelLoader<DeezerImage, InputStream> {
            return DeezerImageLoader(factories.buildModelLoader(GlideUrl::class.java,
                                                                InputStream::class.java))
        }

        override fun teardown() {
            // Do nothing.
        }
    }


    override fun getUrl(model: DeezerImage, width: Int, height: Int): String? {
        return DeezerImageUrlGenerator.buildUrl(model, width, height)
    }
}