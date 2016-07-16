package com.lostincontext.utils;


import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public class Bindings {

    @BindingAdapter("resource")
    public static void setSrc(ImageView view, @DrawableRes int res) {
        view.setImageResource(res);
    }
}
