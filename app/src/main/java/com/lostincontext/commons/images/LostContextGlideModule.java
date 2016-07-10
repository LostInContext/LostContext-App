package com.lostincontext.commons.images;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

public class LostContextGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Do nothing.
    }


    @Override public void registerComponents(Context context, Glide glide) {
        glide.register(DeezerImage.class, InputStream.class, new DeezerImageLoader.Factory());
    }


}