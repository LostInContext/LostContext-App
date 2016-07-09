package com.lostincontext.data;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class DataPlaylist {

    private static final String TAG = DataPlaylist.class.getSimpleName();

    private List<Playlist> data;

    public List<Playlist> getData() {
        return data;
    }

    public void setData(List<Playlist> data) {
        this.data = data;
    }

    public static List<Playlist> deserialize(String data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            final DataPlaylist dataPlaylist = mapper.readValue(data, DataPlaylist.class);
            return dataPlaylist.getData();
        } catch (IOException e) {
            Log.e(TAG, "exception occurred : ", e);
        }
        return Collections.emptyList();
    }

}
