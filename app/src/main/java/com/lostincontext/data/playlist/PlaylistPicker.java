package com.lostincontext.data.playlist;


import com.lostincontext.data.playlist.Playlist;

public class PlaylistPicker {


    private Playlist playlist;

    public PlaylistPicker(Playlist playlist) {
        this.playlist = playlist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
