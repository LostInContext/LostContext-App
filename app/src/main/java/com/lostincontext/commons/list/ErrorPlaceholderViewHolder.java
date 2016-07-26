package com.lostincontext.commons.list;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.lostincontext.databinding.ItemErrorBinding;

public class ErrorPlaceholderViewHolder extends ViewHolder {

    private final ItemErrorBinding binding;

    public ErrorPlaceholderViewHolder(final @NonNull View itemView,
                                      final @NonNull EmptyListCallback callBack) {
        super(itemView);
        binding = ItemErrorBinding.bind(itemView);
        binding.setCallback(callBack);
    }

    private void bindTo(@DrawableRes int errorDrawableRes,
                        CharSequence errorText,
                        CharSequence buttonText,
                        @DrawableRes int buttonIcon) {

        binding.errorImage.setImageResource(errorDrawableRes);
        binding.errorText.setText(errorText);
        binding.errorRefreshButton.setText(buttonText);
        binding.errorRefreshButton.setCompoundDrawablesWithIntrinsicBounds(buttonIcon, 0, 0, 0);
    }


}
