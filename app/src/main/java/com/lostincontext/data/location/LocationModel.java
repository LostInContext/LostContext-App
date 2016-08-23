package com.lostincontext.data.location;


import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.maps.model.LatLng;

public class LocationModel {

    public final double latitude;
    public final double longitude;
    public final @NonNull String placeName;

    @JsonIgnore private LatLng latLng;

    @JsonCreator
    public LocationModel(@JsonProperty("placeName") @NonNull String placeName,
                         @JsonProperty("latitude") double latitude,
                         @JsonProperty("longitude") double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
    }

    public String getPlaceName() {
        return placeName;
    }


    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}
