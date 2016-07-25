package com.lostincontext.data;


import android.support.annotation.DrawableRes;

public class GridBottomSheetItem {

    public final String name;
    @DrawableRes public final int drawableRes;

    public GridBottomSheetItem(String name,
                               @DrawableRes int drawableRes) {
        this.name = name;
        this.drawableRes = drawableRes;
    }
}
