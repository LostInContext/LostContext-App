package com.lostincontext.ruledetails.items;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.commons.list.ViewHolder;
import com.lostincontext.databinding.ItemPlaylistEditScreenBinding;

public class PlaylistInEditScreenViewHolder extends ViewHolder {


    private ItemPlaylistEditScreenBinding binding;

    public static PlaylistInEditScreenViewHolder create(LayoutInflater inflater,
                                                        ViewGroup parent) {
        ItemPlaylistEditScreenBinding itemBinding = ItemPlaylistEditScreenBinding.inflate(inflater,
                                                                                          parent,
                                                                                          false);
        return new PlaylistInEditScreenViewHolder(itemBinding);
    }


    public PlaylistInEditScreenViewHolder(ItemPlaylistEditScreenBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindTo(PlaylistPicker playlistPicker) {
        binding.setPlaylist(playlistPicker.getPlaylist());
    }


}
