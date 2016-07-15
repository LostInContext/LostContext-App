package com.lostincontext.rulescreation.display;


import android.support.v7.widget.RecyclerView;

import com.lostincontext.commons.list.Section;
import com.lostincontext.databinding.ItemSectionHeaderBinding;

public class SectionViewHolder extends RecyclerView.ViewHolder {


    private ItemSectionHeaderBinding binding;

    public SectionViewHolder(ItemSectionHeaderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    public void setContent(Section section) {
        binding.title.setText(section.getTitle());
    }

}
