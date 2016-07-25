package com.lostincontext.ruledetails.items;


import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;

import com.lostincontext.R;
import com.lostincontext.data.GridBottomSheetItem;
import com.lostincontext.data.rules.FenceVM;
import com.lostincontext.ruledetails.RuleDetailsItem;

public class FenceItem implements RuleDetailsItem {

    public final FenceVM fenceVM;

    public final String name;
    @DrawableRes public final int drawableRes;

    public FenceItem(FenceVM fenceVM, String name, int drawableRes) {
        this.fenceVM = fenceVM;
        this.name = name;
        this.drawableRes = drawableRes;
    }


    @IdRes @Override public int getItemViewType() {
        return R.id.view_type_fence_item;
    }

    public static FenceItem createFromPick(GridBottomSheetItem pick, FenceVM fenceVM) {
        return new FenceItem(fenceVM, pick.name, pick.drawableRes);
    }

}
