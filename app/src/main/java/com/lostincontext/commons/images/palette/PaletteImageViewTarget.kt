package com.lostincontext.commons.images.palette


import android.widget.ImageView

import com.bumptech.glide.request.target.ImageViewTarget

class PaletteImageViewTarget(view: ImageView) : ImageViewTarget<PaletteBitmap>(view) {

    override fun setResource(resource: PaletteBitmap) {
        view.setImageBitmap(resource.bitmap)
    }
}
