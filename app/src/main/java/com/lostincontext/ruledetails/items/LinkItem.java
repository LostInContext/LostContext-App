package com.lostincontext.ruledetails.items;


import android.support.annotation.StringRes;

import com.lostincontext.R;
import com.lostincontext.ruledetails.RuleDetailsItem;

public class LinkItem implements RuleDetailsItem {

    public enum Type {
        AND(R.string.and),
        OR(R.string.or);

        @StringRes private final int resourceId;

        Type(int resourceId) {
            this.resourceId = resourceId;
        }

        @StringRes public int getResourceId() {
            return resourceId;
        }

    }

    private Type type;

    public LinkItem() {
        this.type = Type.AND;
    }

    public LinkItem(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override public int getItemViewType() {
        return R.id.view_type_link;
    }
}
