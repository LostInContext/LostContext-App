package com.lostincontext.rulescreation.display;


import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.commons.list.ViewHolder;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.databinding.ItemSectionPlaylistPickBinding;
import com.lostincontext.ruledetails.items.PlaylistPicker;

public class PlaylistPickViewHolder extends ViewHolder implements View.OnClickListener {


    private ItemSectionPlaylistPickBinding binding;

    public static PlaylistPickViewHolder create(LayoutInflater layoutInflater,
                                                ViewGroup parent, RuleCreationItemCallback callback) {

        ItemSectionPlaylistPickBinding itemBinding = ItemSectionPlaylistPickBinding.inflate(layoutInflater,
                                                                                            parent,
                                                                                            false);
        return new PlaylistPickViewHolder(itemBinding, callback);
    }

    public PlaylistPickViewHolder(ItemSectionPlaylistPickBinding binding, RuleCreationItemCallback callback) {
        super(binding.getRoot());
        this.binding = binding;
        binding.setCallback(callback);
    }


    public void bindTo(@Nullable Playlist playlist) {
        if (playlist == null) {
            binding.pickPlaylistText.setText(R.string.pick_playlist);
            binding.icon.setImageResource(R.drawable.ic_plus_16);
        } else {
            String text = binding.getRoot().getResources().getString(R.string.play_playlist, playlist.getTitle());
            binding.pickPlaylistText.setText(text);
        }
    }

    public void bindTo(PlaylistPicker playlistPicker) {
        bindTo(playlistPicker.getPlaylist());
    }

    @Override public void onClick(View view) {

    }


}
