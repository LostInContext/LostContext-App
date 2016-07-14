package com.lostincontext.data.location;


import com.google.android.gms.maps.model.LatLng;

public class LocationModel {

    private LatLng latLng;

    public LocationModel() {
    }


    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
