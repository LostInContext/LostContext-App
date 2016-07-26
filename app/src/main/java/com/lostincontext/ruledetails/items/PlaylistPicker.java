package com.lostincontext.ruledetails.items;


import com.lostincontext.R;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.ruledetails.RuleDetailsItem;

public class PlaylistPicker implements RuleDetailsItem {

    private Playlist playlist;


    public PlaylistPicker(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override public int getItemViewType() {
        return R.id.view_type_playlist_picker;
    }


    public Playlist getPlaylist() {
        return playlist;
    }
}
