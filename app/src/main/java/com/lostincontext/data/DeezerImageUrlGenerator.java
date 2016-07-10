package com.lostincontext.data;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.text.TextUtils;

import com.bumptech.glide.request.target.Target;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class DeezerImageUrlGenerator {


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_COVER, TYPE_ARTIST, TYPE_USER, TYPE_PLAYLIST_CUSTOM_COVER,
            TYPE_MISC})
    public @interface DeezerImageType { }

    public final static int TYPE_COVER = 0;
    public final static int TYPE_ARTIST = 1;
    public final static int TYPE_USER = 2;
    /**
     * <b>CAUTION !</b> This is the type to use for the full cover setted by the users.<br>
     * However, Playlists can also use a patchwork of album covers instead.<br>
     * Use {@code Playlist.getCoverType()} in order to determine which type you need to use.
     */
    public final static int TYPE_PLAYLIST_CUSTOM_COVER = 3;
    public final static int TYPE_MISC = 4;


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({SUB_PATH_COVER, SUB_PATH_ARTIST, SUB_PATH_USER, SUB_PATH_PLAYLIST,
            SUB_PATH_MISC})
    public @interface DeezerImageSubPath { }

    public final static String SUB_PATH_COVER = "cover";
    public final static String SUB_PATH_ARTIST = "artist";
    public final static String SUB_PATH_USER = "user";
    public final static String SUB_PATH_PLAYLIST = "playlist";
    public final static String SUB_PATH_MISC = "misc";

    private final static String TAG__IMAGES_URL = "HOST_IMAGES";


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({IMAGE_SPEC_JPG, IMAGE_SPEC_PNG})
    public @interface DeezerImageSpec { }

    /**
     * image spec : background color - compression - mode - version .jpg
     */
    public static final String IMAGE_SPEC_JPG = "-000000-80-0-0.jpg";

    /**
     * image spec : background color - compression - mode - version .jpg<br>
     * {@code none} = transparent for some reason
     */
    public static final String IMAGE_SPEC_PNG = "-none-100-0-0.png";

    /**
     * the image buckets provided by our API
     */
    @SuppressWarnings("MagicNumber")
    private final static int[] IMAGE_BUCKETS = {60, 120, 200, 340, 400, 500, 720};


    private static String imageUrlPrefix = "http://cdn-images.deezer.com/images/";


    @Nullable
    public static String buildStandardUrl(final @Nullable String md5,
                                          final @DeezerImageType int type,
                                          final int width,
                                          final int height) {

        if (TextUtils.isEmpty(md5)) {
            return null;
        }
        @DeezerImageSubPath String subPath;
        switch (type) {
            case TYPE_COVER:
                subPath = SUB_PATH_COVER;
                break;

            case TYPE_ARTIST:
                subPath = SUB_PATH_ARTIST;
                break;

            case TYPE_PLAYLIST_CUSTOM_COVER:
                subPath = SUB_PATH_PLAYLIST;
                break;

            case TYPE_USER:
                subPath = SUB_PATH_USER;
                break;

            case TYPE_MISC:
            default:
                subPath = SUB_PATH_MISC;
                break;
        }

        final int size = getSquareImageBucket(width, height);
        return imageUrlPrefix + subPath + "/" + md5 + "/" + size + "x" + size + IMAGE_SPEC_JPG;
    }


    private static int getSquareImageBucket(int width, int height) {
        if (width == Target.SIZE_ORIGINAL) {
            // we provide the biggest bucket for this configuration :
            return IMAGE_BUCKETS[IMAGE_BUCKETS.length - 1];
        }

        final int maxSide = Math.max(width, height);

        for (int bucket : IMAGE_BUCKETS) {
            if (bucket >= maxSide) {
                return bucket;
            }
        }
        return IMAGE_BUCKETS[IMAGE_BUCKETS.length - 1];
    }


}
