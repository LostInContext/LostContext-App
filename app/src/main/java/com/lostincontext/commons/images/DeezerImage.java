package com.lostincontext.commons.images;


import com.lostincontext.commons.images.DeezerImageUrlGenerator.DeezerImageType;

public interface DeezerImage {

    String getCoverMd5();

    @DeezerImageType int getImageType();

}
