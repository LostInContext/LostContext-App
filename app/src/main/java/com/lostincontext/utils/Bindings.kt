package com.lostincontext.utils


import android.databinding.BindingAdapter
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lostincontext.data.playlist.Playlist


@BindingAdapter("resource")
fun setSrc(imageView: ImageView, @DrawableRes res: Int) {
    imageView.setImageResource(res)
}


@BindingAdapter("glide_playlist")
fun setImageUrl(imageView: ImageView, playlist: Playlist) {
    Glide.with(imageView.context).load(playlist).into(imageView)
}