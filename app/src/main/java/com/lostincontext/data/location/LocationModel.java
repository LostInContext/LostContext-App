package com.lostincontext.data.location;


import com.google.android.gms.maps.model.LatLng;

public class LocationModel {

    public double latitude;
    public double longitude;
    public String placeName;
    private LatLng latLng;

    public LocationModel() {
    }

    public LocationModel(String placeName, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LatLng getLatLng() {
        if (latLng == null) {
            latLng = new LatLng(latitude, longitude);
        }
        return latLng;
    }
}
