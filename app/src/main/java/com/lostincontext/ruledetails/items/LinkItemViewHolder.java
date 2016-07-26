package com.lostincontext.ruledetails.items;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.commons.list.ViewHolder;
import com.lostincontext.databinding.ItemLinkBinding;

public class LinkItemViewHolder extends ViewHolder {

    private ItemLinkBinding binding;


    public static LinkItemViewHolder create(LayoutInflater inflater,
                                            ViewGroup parent) {
        ItemLinkBinding itemBinding = ItemLinkBinding.inflate(inflater,
                                                              parent,
                                                              false);
        return new LinkItemViewHolder(itemBinding);
    }


    public LinkItemViewHolder(ItemLinkBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindTo(LinkItem item) {
        binding.setItem(item);
    }
}
