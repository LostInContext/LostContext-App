package com.lostincontext.rulecreate

import com.genius.groupie.Item
import com.lostincontext.R
import com.lostincontext.data.rules.FenceNamer
import com.lostincontext.data.rulesV2.AtomicCondition
import com.lostincontext.databinding.ItemAtomicConditionBinding


class AtomicConditionItem constructor(val callback: Callback,
                                      val atomic: AtomicCondition,
                                      val isFirst: Boolean,
                                      val namer: FenceNamer) :
        Item<ItemAtomicConditionBinding>() {

    interface Callback {
        fun onDeleteButtonClick(atomic: AtomicCondition)
        fun onToggleClick(atomic: AtomicCondition)
    }


    override fun bind(viewBinding: ItemAtomicConditionBinding, position: Int) {
        viewBinding.callback = callback
        viewBinding.viewModel = this
    }

    override fun getLayout() = R.layout.item_atomic_condition


    fun getModifierRes(): Int {
        if (isFirst) {
            when (atomic.modifier) {
                AtomicCondition.Modifier.NONE -> return R.string.`when`
                AtomicCondition.Modifier.NOT -> return R.string.when_not
            }
        } else {
            when (atomic.modifier) {
                AtomicCondition.Modifier.NONE -> return R.string.and
                AtomicCondition.Modifier.NOT -> return R.string.and_not
            }
        }
    }

    fun getName() = atomic.fence.name(namer)

}
