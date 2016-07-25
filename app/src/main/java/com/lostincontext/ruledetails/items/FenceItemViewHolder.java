package com.lostincontext.ruledetails.items;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.commons.list.ViewHolder;
import com.lostincontext.databinding.ItemFenceBinding;

public class FenceItemViewHolder extends ViewHolder {


    private ItemFenceBinding binding;

    public static FenceItemViewHolder create(LayoutInflater inflater,
                                             ViewGroup parent) {
        ItemFenceBinding itemBinding = ItemFenceBinding.inflate(inflater,
                                                                parent,
                                                                false);
        return new FenceItemViewHolder(itemBinding);
    }


    public FenceItemViewHolder(ItemFenceBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindTo(FenceItem item) {
        binding.setItem(item);
    }


}
