package com.lostincontext.rulescreation.display;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.commons.list.Section;
import com.lostincontext.commons.list.ViewHolder;
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
        binding.title.setText(section.getTitle());
    }

}
