package com.lostincontext.ruledetails.items;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.commons.list.ViewHolder;
import com.lostincontext.databinding.ItemFenceBinding;

import java.util.List;

import static com.lostincontext.ruledetails.RuleDetailsContract.LINK_CHANGED;

public class FenceItemViewHolder extends ViewHolder {


    private ItemFenceBinding binding;

    public static FenceItemViewHolder create(LayoutInflater inflater,
                                             ViewGroup parent,
                                             FenceItemCallback callback) {
        ItemFenceBinding itemBinding = ItemFenceBinding.inflate(inflater,
                                                                parent,
                                                                false);
        return new FenceItemViewHolder(itemBinding, callback);
    }


    public FenceItemViewHolder(ItemFenceBinding binding, FenceItemCallback callback) {
        super(binding.getRoot());
        this.binding = binding;
        binding.setCallback(callback);
    }

    public void bindTo(FenceItem item, List<Object> payloads) {
        if (payloads.isEmpty()) binding.setItem(item);
        else {
            for (int i = 0, size = payloads.size(); i < size; i++) {
                if ((payloads.get(i)) == LINK_CHANGED) {
                    binding.linkTextView.setText(item.link.getResourceId());
                }

            }
        }
    }


}
