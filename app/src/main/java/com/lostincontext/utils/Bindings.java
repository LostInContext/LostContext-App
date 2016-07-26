package com.lostincontext.utils;


import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lostincontext.data.playlist.Playlist;

public class Bindings {

    @BindingAdapter("resource")
    public static void setSrc(ImageView view, @DrawableRes int res) {
        view.setImageResource(res);
    }


    @BindingAdapter("glide_playlist")
    public static void setImageUrl(ImageView imageView, Playlist playlist) {
        Glide.with(imageView.getContext()).load(playlist).into(imageView);
    }
}
