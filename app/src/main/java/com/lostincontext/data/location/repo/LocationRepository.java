package com.lostincontext.data.location.repo;


import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;
import com.lostincontext.data.location.LocationModel;
import com.lostincontext.data.rules.FenceVM;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class LocationRepository {


    interface LoadTasksCallback {

        void onTasksLoaded(List<FenceVM> rules);

        void onTasksLoadFailure();
    }

    private static final String TAG = LocationRepository.class.getSimpleName();

    private final SharedPreferences preferences;
    private final ObjectMapper objectMapper;

    @Inject
    public LocationRepository(@Named("location") SharedPreferences preferences,
                              @Named("location") ObjectMapper objectMapper) {
        this.preferences = preferences;
        this.objectMapper = objectMapper;
    }

    public void saveLocation(String name, LocationModel locationModel) {
        try {
            saveToPrefs(name, serialize(locationModel));
        } catch (JsonProcessingException e) {
            Log.e(TAG, "save: ", e);
        }
    }

    public void saveLocation(String name, LatLng latLng) {
        LocationModel model = new LocationModel(name, latLng.latitude, latLng.longitude);
        try {
            saveToPrefs(name, serialize(model));
        } catch (JsonProcessingException e) {
            Log.e(TAG, "save: ", e);
        }
    }

    @Nullable
    public LocationModel getLocation(String name) {
        String json = loadFromPrefs(name);
        if (json.isEmpty()) return null;
        try {
            return deserialize(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //region SharedPreferences editor

    private void saveToPrefs(String title, String json) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(title, json);
        editor.apply();

        Log.i(TAG, "saving: " + json);
    }

    private String loadFromPrefs(String title) {
        final String data = preferences.getString(title, "");
        Log.i(TAG, "loading: " + data);
        return data;
    }

    //endregion


    //region Serializer / deserializer

    private String serialize(LocationModel locationModel) throws JsonProcessingException {
        return objectMapper.writeValueAsString(locationModel);

    }

    private LocationModel deserialize(String json) throws IOException {
        return objectMapper.readerFor(LocationModel.class).readValue(json);
    }
    //endregion

}
