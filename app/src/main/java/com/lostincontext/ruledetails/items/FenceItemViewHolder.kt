package com.lostincontext.ruledetails.items


import android.view.LayoutInflater
import android.view.ViewGroup

import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.databinding.ItemFenceBinding

import com.lostincontext.ruledetails.RuleDetailsContract.LINK_CHANGED

class FenceItemViewHolder(private val binding: ItemFenceBinding, callback: FenceItemCallback)
: ViewHolder(binding.root) {


    init {
        binding.callback = callback
    }

    fun bindTo(item: FenceItem, payloads: List<Any>) {
        if (payloads.isEmpty())
            binding.item = item
        else {
            var i = 0
            val size = payloads.size
            while (i < size) {
                if (payloads[i] === LINK_CHANGED) {
                    binding.linkTextView.setText(item.link.resourceId)
                }
                i++

            }
        }
    }

    companion object {

        fun create(inflater: LayoutInflater,
                   parent: ViewGroup,
                   callback: FenceItemCallback): FenceItemViewHolder {
            val itemBinding = ItemFenceBinding.inflate(inflater,
                                                       parent,
                                                       false)
            return FenceItemViewHolder(itemBinding, callback)
        }
    }


}
