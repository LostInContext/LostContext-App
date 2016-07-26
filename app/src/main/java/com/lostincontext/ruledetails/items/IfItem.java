package com.lostincontext.ruledetails.items;


import com.lostincontext.R;
import com.lostincontext.ruledetails.RuleDetailsItem;

public class IfItem implements RuleDetailsItem {


    public enum Type {
        AND, OR
    }

    private Type type;

    public IfItem() {
        this.type = Type.AND;
    }

    public IfItem(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    @Override public int getItemViewType() {
        return R.id.view_type_if;
    }
}
