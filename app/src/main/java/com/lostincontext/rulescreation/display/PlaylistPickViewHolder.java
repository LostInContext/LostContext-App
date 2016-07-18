package com.lostincontext.rulescreation.display;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lostincontext.R;
import com.lostincontext.data.Playlist;
import com.lostincontext.databinding.ItemSectionPlaylistPickBinding;

public class PlaylistPickViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private ItemSectionPlaylistPickBinding binding;

    public PlaylistPickViewHolder(ItemSectionPlaylistPickBinding binding, RuleCreationItemCallback callback) {
        super(binding.getRoot());
        this.binding = binding;
        binding.setCallback(callback);
    }


    public void setContent(@Nullable Playlist playlist) {
        if (playlist == null) {
            binding.pickPlaylistText.setText("Pick a playlist to play !");
            binding.icon.setImageResource(R.drawable.ic_plus_16);
        } else {
            binding.pickPlaylistText.setText("play" + playlist.getTitle());
        }
    }

    @Override public void onClick(View view) {

    }


}
