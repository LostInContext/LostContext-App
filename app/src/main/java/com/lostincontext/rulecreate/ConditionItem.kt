package com.lostincontext.rulecreate

import com.genius.groupie.Item
import com.lostincontext.R
import com.lostincontext.data.rulesV2.Condition
import com.lostincontext.databinding.ItemConditionBinding


class ConditionItem constructor(val callback: Callback,
                                val list: List<ConditionItem>,
                                val condition: Condition,
                                scribe: Scribe) :
        Item<ItemConditionBinding>() {

    val text: CharSequence = scribe.describeCondition(condition)

    var binding: ItemConditionBinding? = null


    interface Callback {
        fun onDeleteButtonClick(condition: Condition)
        fun onConditionClick(condition: Condition)
    }


    override fun bind(viewBinding: ItemConditionBinding, position: Int) {
        viewBinding.viewModel = this
        viewBinding.callback = callback
        binding = viewBinding
    }

    override fun getLayout() = R.layout.item_condition

    fun getDisplayPosition(): Int {
        return list.indexOf(this) + 1
    }

}
