package com.lostincontext.ruledetails.pick


import android.support.v7.widget.RecyclerView

import com.lostincontext.R
import com.lostincontext.commons.list.Section
import com.lostincontext.ruledetails.pick.GridBottomSheetItem


class BottomSheetItemSection(title: String, fenceCreators: List<GridBottomSheetItem>)
: Section<GridBottomSheetItem>(title,
                               fenceCreators,
                               R.id.view_type_grid_bottom_sheet_item) {

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BottomSheetGridItemViewHolder).bindTo(get(position))
    }
}

