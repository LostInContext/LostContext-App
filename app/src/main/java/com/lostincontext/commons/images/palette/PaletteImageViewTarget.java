package com.lostincontext.commons.images.palette;


import android.widget.ImageView;

import com.bumptech.glide.request.target.ImageViewTarget;

public class PaletteImageViewTarget extends ImageViewTarget<PaletteBitmap> {


    public PaletteImageViewTarget(ImageView view) {
        super(view);
    }

    @Override protected void setResource(PaletteBitmap resource) {
        view.setImageBitmap(resource.bitmap);
    }
}
