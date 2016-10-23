package com.lostincontext.utils


import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.lostincontext.commons.images.DeezerImage


@BindingAdapter("resource")
fun setSrc(imageView: ImageView, @DrawableRes res: Int) {
    imageView.setImageResource(res)
}


@BindingAdapter("deezer_image")
fun setImageUrl(imageView: ImageView, image: DeezerImage?) {
    Glide.with(imageView.context)
            .load(image)
            .into(imageView)
}


@BindingAdapter("deezer_image", "placeholder")
fun setImageUrl(imageView: ImageView, image: DeezerImage?, placeholder: Drawable) {
    Glide.with(imageView.context)
            .load(image)
            .placeholder(placeholder)
            .into(imageView)
}


@BindingAdapter("deezer_image", "placeholder", "transformation")
fun setImageUrl(imageView: ImageView, image: DeezerImage?,
                placeholder: Drawable,
                transformation: BitmapTransformation) {
    Glide.with(imageView.context)
            .load(image)
            .placeholder(placeholder)
            .transform(transformation)
            .into(imageView)
}