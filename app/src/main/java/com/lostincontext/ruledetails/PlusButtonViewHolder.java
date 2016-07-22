package com.lostincontext.ruledetails;

import android.support.v7.widget.RecyclerView;

import com.lostincontext.databinding.ItemPlusButtonBinding;
import com.lostincontext.databinding.ItemSectionItemRuleCreationBinding;
import com.lostincontext.rulescreation.display.RuleCreationItemCallback;

public class PlusButtonViewHolder extends RecyclerView.ViewHolder {

    private final ItemSectionItemRuleCreationBinding itemBinding;

    public PlusButtonViewHolder(ItemPlusButtonBinding itemBinding,
                                RuleCreationItemCallback callback) {
        super(itemBinding.getRoot());
        itemBinding.setCallback(callback);
        this.itemBinding = itemBinding;
    }
}
