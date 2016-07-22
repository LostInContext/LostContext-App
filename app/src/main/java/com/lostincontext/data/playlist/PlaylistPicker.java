package com.lostincontext.data.playlist;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PlaylistPicker {


    private Playlist playlist;

    public PlaylistPicker() {}

    public PlaylistPicker(Playlist playlist) {
        this.playlist = playlist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    //region Serializer / deserializer

    public String serialize() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);

    }

    public static PlaylistPicker deserialize(String json) throws IOException {
        return new ObjectMapper().readerFor(PlaylistPicker.class).readValue(json);
    }
    //endregion
}
