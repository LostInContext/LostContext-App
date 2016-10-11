package com.lostincontext.rulecreate

import com.genius.groupie.Item
import com.genius.groupie.UpdatingGroup
import com.lostincontext.R
import com.lostincontext.data.rulesV2.Condition
import com.lostincontext.databinding.ItemConditionBinding


class ConditionItem constructor(val callback: Callback,
                                val position: Int,
                                val condition: Condition) :
        Item<ItemConditionBinding>(),
        UpdatingGroup.Comparable<ConditionItem> {


    interface Callback {
        fun onDeleteButtonClick(condition: Condition)
        fun onConditionClick(condition: Condition)
    }


    override fun bind(viewBinding: ItemConditionBinding, position: Int) {
        viewBinding.viewModel = this
        viewBinding.callback = callback
    }

    override fun getLayout() = R.layout.item_condition


    override fun areContentsTheSame(other: ConditionItem?) = true

    override fun areItemsTheSame(other: ConditionItem) = position == other.position

}
