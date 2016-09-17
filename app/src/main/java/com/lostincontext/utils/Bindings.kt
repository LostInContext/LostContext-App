package com.lostincontext.utils


import android.databinding.BindingAdapter
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lostincontext.commons.images.DeezerImage
import com.lostincontext.data.playlist.Playlist


@BindingAdapter("resource")
fun setSrc(imageView: ImageView, @DrawableRes res: Int) {
    imageView.setImageResource(res)
}


@BindingAdapter("deezer_image")
fun setImageUrl(imageView: ImageView, image: DeezerImage) {
    Glide.with(imageView.context).load(image).into(imageView)
}