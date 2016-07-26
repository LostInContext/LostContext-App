package com.lostincontext.ruledetails.items;


import android.support.annotation.Nullable;

import com.lostincontext.R;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.ruledetails.RuleDetailsItem;

public class PlaylistPicker implements RuleDetailsItem {

    private @Nullable Playlist playlist;

    @Override public int getItemViewType() {
        return R.id.view_type_playlist_picker;
    }

    @Nullable public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(@Nullable Playlist playlist) {
        this.playlist = playlist;
    }

}
