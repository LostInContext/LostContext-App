package com.lostincontext.ruledetails.items;


import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.lostincontext.R;
import com.lostincontext.ruledetails.pick.GridBottomSheetItem;
import com.lostincontext.data.rules.FenceVM;
import com.lostincontext.data.rules.NotFenceVM;

import static com.lostincontext.ruledetails.items.FenceItem.Link.AND;
import static com.lostincontext.ruledetails.items.FenceItem.Link.OR;
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

    /**
     * duplicates an object but replaces its fenceVM & link attributes
     */
    public FenceItem(FenceItem item,
                     FenceVM fenceVM,
                     Link link) {
        this.fenceVM = fenceVM;
        this.name = item.name;
        this.drawableRes = item.drawableRes;
        this.link = link;

    }

    /**
     * If the item link is not of the 'NOT' variety, this just returns the item,
     * Otherwise, it returns a new item without the not but with a wrapping NotFenceVM
     */
    public static FenceItem wrapNot(FenceItem item) {
        switch (item.link) {
            case AND:
            case OR:
            case WHEN:
                return item;

            case AND_NOT:
                return new FenceItem(item, new NotFenceVM(item.fenceVM), AND);
            case OR_NOT:
                return new FenceItem(item, new NotFenceVM(item.fenceVM), OR);
        }
        throw new RuntimeException("unhandled case");
    }

    public static FenceItem createFromPick(GridBottomSheetItem pick, FenceVM fenceVM, boolean isFirstItem) {
        return new FenceItem(fenceVM, pick.name, pick.drawableRes, isFirstItem);
    }

    public boolean isWhen() {
        return link.equals(WHEN);
    }

}
