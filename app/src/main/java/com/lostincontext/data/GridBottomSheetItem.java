package com.lostincontext.data;


import android.support.annotation.DrawableRes;

import com.lostincontext.ruledetails.RuleDetailsPresenter;

public class GridBottomSheetItem {

    public final String name;
    @DrawableRes public final int drawableRes;
    public final RuleDetailsPresenter.Picker picker;

    public GridBottomSheetItem(String name,
                               @DrawableRes int drawableRes,
                               RuleDetailsPresenter.Picker picker) {
        this.name = name;
        this.drawableRes = drawableRes;
        this.picker = picker;
    }
}
