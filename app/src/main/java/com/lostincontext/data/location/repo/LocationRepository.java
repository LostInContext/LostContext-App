package com.lostincontext.data.location.repo;


import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;
import com.lostincontext.data.location.LocationModel;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

public class LocationRepository {


    public interface LocationCallback {

        void onLocationFetched(LocationModel locationModel);

        void onLocationLoadFailed(String name);
    }

    private static final String TAG = LocationRepository.class.getSimpleName();

    private final SharedPreferences preferences;
    private final ObjectMapper objectMapper;

    @Inject
    public LocationRepository(@Named("location") SharedPreferences preferences,
                              ObjectMapper objectMapper) {
        this.preferences = preferences;
        this.objectMapper = objectMapper;
    }

    public void saveLocation(String name, LatLng latLng) {
        LocationModel model = new LocationModel(name, latLng.latitude, latLng.longitude);
        try {
            saveToPrefs(name, serialize(model));
        } catch (JsonProcessingException e) {
            Log.e(TAG, "save: ", e);
        }
    }

    public void getLocation(String name, LocationCallback callback) {
        String json = loadFromPrefs(name);
        if (json.isEmpty()) callback.onLocationLoadFailed(name);
        try {
            callback.onLocationFetched(deserialize(json));
        } catch (IOException e) {
            e.printStackTrace();
            callback.onLocationLoadFailed(name);
        }
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
