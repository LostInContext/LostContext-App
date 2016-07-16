package com.lostincontext.data;


import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

public class FenceCreator {

    public String name;
    @DrawableRes public int drawableRes;
    @ColorInt public int selectedColor = Color.RED;

    public boolean selected;

    public FenceCreator(String name, int drawableRes, int selectedColor) {
        this.name = name;
        this.drawableRes = drawableRes;
        this.selectedColor = selectedColor;
    }
}
