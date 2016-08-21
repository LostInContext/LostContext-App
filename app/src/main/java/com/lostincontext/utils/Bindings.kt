package com.lostincontext.utils


import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.lostincontext.R
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.ruledetails.items.FenceItem


@BindingAdapter("resource")
fun setSrc(imageView: ImageView, @DrawableRes res: Int) {
    imageView.setImageResource(res)
}


@BindingAdapter("glide_playlist")
fun setImageUrl(imageView: ImageView, playlist: Playlist) {
    Glide.with(imageView.context).load(playlist).into(imageView)
}

fun getWhenItemBackground(context: Context, item: FenceItem): Drawable {
    if (item.isWhen()) return ColorDrawable(Color.TRANSPARENT)
    return context.getDrawable(R.drawable.app_btn_background_material)
}
