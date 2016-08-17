package com.lostincontext.utils;


import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lostincontext.R;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.ruledetails.items.FenceItem;

public class Bindings {

    @BindingAdapter("resource")
    public static void setSrc(ImageView view, @DrawableRes int res) {
        view.setImageResource(res);
    }


    @BindingAdapter("glide_playlist")
    public static void setImageUrl(ImageView imageView, Playlist playlist) {
        Glide.with(imageView.getContext()).load(playlist).into(imageView);
    }

    public static Drawable getWhenItemBackground(Context context, FenceItem item) {
        if (item.isWhen()) return new ColorDrawable(Color.TRANSPARENT);
        return context.getDrawable(R.drawable.app_btn_background_material);
    }
}
