package com.lostincontext.ruledetails.items;


import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.lostincontext.R;
import com.lostincontext.data.GridBottomSheetItem;
import com.lostincontext.data.rules.FenceVM;

import static com.lostincontext.ruledetails.items.FenceItem.Link.AND;
import static com.lostincontext.ruledetails.items.FenceItem.Link.WHEN;

public class FenceItem {

    public final FenceVM fenceVM;

    public final String name;
    @DrawableRes public final int drawableRes;
    public Link link;


    public enum Link {
        AND(R.string.and),
        OR(R.string.or),
        AND_NOT(R.string.and_not),
        OR_NOT(R.string.or_not),
        WHEN(R.string.when);

        @StringRes private final int resourceId;

        Link(int resourceId) {
            this.resourceId = resourceId;
        }

        @StringRes public int getResourceId() {
            return resourceId;
        }
    }


    public FenceItem(FenceVM fenceVM,
                     String name,
                     int drawableRes,
                     boolean isFirstItem) {
        this.fenceVM = fenceVM;
        this.name = name;
        this.drawableRes = drawableRes;
        if (isFirstItem) link = WHEN;
        else link = AND;
    }

    public static FenceItem createFromPick(GridBottomSheetItem pick, FenceVM fenceVM, boolean isFirstItem) {
        return new FenceItem(fenceVM, pick.name, pick.drawableRes, isFirstItem);
    }


}
