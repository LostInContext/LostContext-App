package com.lostincontext.ruledetails.pick;


import android.support.v7.widget.RecyclerView;

import com.lostincontext.R;
import com.lostincontext.commons.list.Section;
import com.lostincontext.data.GridBottomSheetItem;

import java.util.List;


public class BottomSheetItemSection extends Section<GridBottomSheetItem> {

    public BottomSheetItemSection(String title, List<GridBottomSheetItem> fenceCreators) {
        super(title, fenceCreators, R.id.view_type_grid_bottom_sheet_item);
    }

    @Override protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BottomSheetGridItemViewHolder) holder).bindTo(get(position));
    }
}

