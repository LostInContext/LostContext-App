package com.lostincontext.ruledetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.databinding.ItemPlusButtonBinding;
import com.lostincontext.rulescreation.display.RuleCreationItemCallback;

public class PlusButtonViewHolder extends RecyclerView.ViewHolder {

    private final ItemPlusButtonBinding itemBinding;

    public static PlusButtonViewHolder create(LayoutInflater layoutInflater, ViewGroup parent,
                                              RuleCreationItemCallback callback) {
        ItemPlusButtonBinding plusButtonBinding = ItemPlusButtonBinding.inflate(layoutInflater,
                                                                                parent,
                                                                                false);
        return new PlusButtonViewHolder(plusButtonBinding, callback);

    }


    private PlusButtonViewHolder(ItemPlusButtonBinding itemBinding,
                                 RuleCreationItemCallback callback) {
        super(itemBinding.getRoot());
        // itemBinding.setCallback(callback);
        this.itemBinding = itemBinding;
    }


}
