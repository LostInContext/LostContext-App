package com.lostincontext.data.rules;


import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lostincontext.data.playlist.Playlist;

public class Rule {

    @NonNull private final String name;
    @NonNull private final FenceVM fenceVM;
    @NonNull private final Playlist playlist;


    @JsonCreator
    public Rule(@NonNull @JsonProperty("name") String name,
                @NonNull @JsonProperty("fenceVM") FenceVM fenceVM,
                @NonNull @JsonProperty("playlist") Playlist playlist) {
        this.name = name;
        this.fenceVM = fenceVM;
        this.playlist = playlist;
    }


    @NonNull public String getName() {
        return name;
    }

    @NonNull public FenceVM getFenceVM() {
        return fenceVM;
    }

    @NonNull public Playlist getPlaylist() {
        return playlist;
    }


}
