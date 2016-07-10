package com.lostincontext.commons.images;


import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

public class DeezerImageLoader extends BaseGlideUrlLoader<DeezerImage> {


    public static class Factory implements ModelLoaderFactory<DeezerImage, InputStream> {

        @Override
        public ModelLoader<DeezerImage, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new DeezerImageLoader(factories.buildModelLoader(GlideUrl.class, InputStream.class));
        }

        @Override
        public void teardown() {
            // Do nothing.
        }
    }

    public DeezerImageLoader(ModelLoader<GlideUrl, InputStream> urlLoader) {
        super(urlLoader);
    }


    @Override protected String getUrl(DeezerImage model, int width, int height) {
        return DeezerImageUrlGenerator.buildUrl(model, width, height);
    }
}