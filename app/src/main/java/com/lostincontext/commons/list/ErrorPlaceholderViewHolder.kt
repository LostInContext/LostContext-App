package com.lostincontext.commons.list

import android.support.annotation.DrawableRes
import android.view.View

import com.lostincontext.databinding.ItemErrorBinding

class ErrorPlaceholderViewHolder(itemView: View,
                                 callBack: EmptyListCallback) : ViewHolder(itemView) {

    private val binding: ItemErrorBinding

    init {
        binding = ItemErrorBinding.bind(itemView)
        binding.callback = callBack
    }

    private fun bindTo(@DrawableRes errorDrawableRes: Int,
                       errorText: CharSequence,
                       buttonText: CharSequence,
                       @DrawableRes buttonIcon: Int) {

        binding.errorImage.setImageResource(errorDrawableRes)
        binding.errorText.text = errorText
        binding.errorRefreshButton.text = buttonText
        binding.errorRefreshButton.setCompoundDrawablesWithIntrinsicBounds(buttonIcon, 0, 0, 0)
    }


}
