package com.lostincontext.ruledetails.pick


import android.view.LayoutInflater
import android.view.ViewGroup

import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.data.GridBottomSheetItem
import com.lostincontext.databinding.ItemBottomSheetGridBinding

class BottomSheetGridItemViewHolder(private val binding: ItemBottomSheetGridBinding,
                                    callback: PickerDialogCallback)
: ViewHolder(binding.root) {


    init {
        binding.callback = callback
    }

    fun bindTo(item: GridBottomSheetItem) {
        binding.item = item
    }

    companion object {

        fun create(inflater: LayoutInflater,
                   parent: ViewGroup,
                   callback: PickerDialogCallback): BottomSheetGridItemViewHolder {
            val itemBinding = ItemBottomSheetGridBinding.inflate(inflater,
                                                                 parent,
                                                                 false)
            return BottomSheetGridItemViewHolder(itemBinding, callback)
        }
    }


}
