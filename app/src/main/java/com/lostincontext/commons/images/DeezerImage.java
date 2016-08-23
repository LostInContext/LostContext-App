package com.lostincontext.commons.images;


import android.support.annotation.Nullable;

import com.lostincontext.commons.images.DeezerImageUrlGenerator.DeezerImageType;

public interface DeezerImage {

    @Nullable  String getCoverMd5();

    @DeezerImageType int getImageType();

}
