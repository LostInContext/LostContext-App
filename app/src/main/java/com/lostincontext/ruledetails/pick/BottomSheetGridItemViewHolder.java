package com.lostincontext.ruledetails.pick;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.commons.list.ViewHolder;
import com.lostincontext.data.GridBottomSheetItem;
import com.lostincontext.databinding.ItemBottomSheetGridBinding;
import com.lostincontext.rulescreation.display.RuleCreationItemCallback;

public class BottomSheetGridItemViewHolder extends ViewHolder {


    private ItemBottomSheetGridBinding binding;

    public static BottomSheetGridItemViewHolder create(LayoutInflater inflater,
                                                       ViewGroup parent,
                                                       RuleCreationItemCallback callback) {
        ItemBottomSheetGridBinding itemBinding = ItemBottomSheetGridBinding.inflate(inflater,
                                                                                    parent,
                                                                                    false);
        return new BottomSheetGridItemViewHolder(itemBinding, callback);
    }


    public BottomSheetGridItemViewHolder(ItemBottomSheetGridBinding binding,
                                         RuleCreationItemCallback callback) {
        super(binding.getRoot());
        this.binding = binding;
        binding.setCallback(callback);
    }

    public void bindTo(GridBottomSheetItem item) {
        binding.setItem(item);
    }


}
