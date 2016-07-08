package com.lostincontext.data;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by STrabelsi on 07/07/2016.
 */

public class DataPlaylist {

    private List<Playlist> data;

    public List<Playlist> getData() {
        return data;
    }

    public void setData(List<Playlist> data) {
        this.data = data;
    }

    public List<Playlist> deserialize(String data) {
        Gson json = new Gson();
        final DataPlaylist dataPlaylist = json.fromJson(data, DataPlaylist.class);
        return dataPlaylist.getData();
    }

}
