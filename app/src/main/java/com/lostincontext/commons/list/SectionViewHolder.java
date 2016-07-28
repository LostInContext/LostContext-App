package com.lostincontext.commons.list;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.databinding.ItemSectionHeaderBinding;

public class SectionViewHolder extends ViewHolder {


    private ItemSectionHeaderBinding binding;

    public static SectionViewHolder create(LayoutInflater layoutInflater,
                                           ViewGroup parent) {
        ItemSectionHeaderBinding headerBinding = ItemSectionHeaderBinding.inflate(layoutInflater,
                                                                                  parent,
                                                                                  false);
        return new SectionViewHolder(headerBinding);
    }


    public SectionViewHolder(ItemSectionHeaderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    public void bindTo(Section section) {
        binding.setTitle(section.getTitle());
    }

}
