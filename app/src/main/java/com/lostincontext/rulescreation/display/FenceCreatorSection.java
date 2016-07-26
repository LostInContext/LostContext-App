package com.lostincontext.rulescreation.display;


import android.support.v7.widget.RecyclerView;

import com.lostincontext.commons.list.Section;
import com.lostincontext.data.FenceCreator;

import java.util.List;

public class FenceCreatorSection extends Section<FenceCreator> {

    public FenceCreatorSection(String title, List<FenceCreator> fenceCreators, int itemViewType) {
        super(title, fenceCreators, itemViewType);
    }

    @Override protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RuleCreatorViewHolder) holder).bindTo(get(position));
    }
}
