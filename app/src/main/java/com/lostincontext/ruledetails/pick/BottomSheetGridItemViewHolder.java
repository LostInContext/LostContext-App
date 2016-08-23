package com.lostincontext.ruledetails.pick;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.commons.list.ViewHolder;
import com.lostincontext.databinding.ItemBottomSheetGridBinding;

public class BottomSheetGridItemViewHolder extends ViewHolder {


    private ItemBottomSheetGridBinding binding;

    public static BottomSheetGridItemViewHolder create(LayoutInflater inflater,
                                                       ViewGroup parent,
                                                       PickerDialogCallback callback) {
        ItemBottomSheetGridBinding itemBinding = ItemBottomSheetGridBinding.inflate(inflater,
                                                                                    parent,
                                                                                    false);
        return new BottomSheetGridItemViewHolder(itemBinding, callback);
    }


    public BottomSheetGridItemViewHolder(ItemBottomSheetGridBinding binding,
                                         PickerDialogCallback callback) {
        super(binding.getRoot());
        this.binding = binding;
        binding.setCallback(callback);
    }

    public void bindTo(GridBottomSheetItem item) {
        binding.setItem(item);
    }


}
