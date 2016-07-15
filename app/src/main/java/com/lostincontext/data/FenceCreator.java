package com.lostincontext.data;


import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

public class FenceCreator {

    public String name;
    @DrawableRes public int DrawableRes;
    @ColorInt public int selectedColor = Color.RED;

    public boolean selected;

}
