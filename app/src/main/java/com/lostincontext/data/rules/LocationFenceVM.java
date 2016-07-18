package com.lostincontext.data.rules;

import android.support.annotation.StringDef;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.maps.model.LatLng;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class LocationFenceVM implements FenceVM {

    public enum State {
        IN,
        ENTERING,
        EXITING
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            HOME,
            WORK
    })
    public @interface LocationName { }

    public static final String HOME = "home";
    public static final String WORK = "work";


    private State state;
    private double latitude;
    private double longitude;
    private double radius;
    @LocationName private String name;

    private long dwellTimeMillis = 10000; //10 seconds

    private LocationFenceVM() { }

    public LocationFenceVM(@LocationName String locationName, LatLng latLng) {
        this.state = State.IN;
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        this.radius = 10;//in meters
        this.name = locationName;
    }


    @Override public AwarenessFence build(FenceBuilder builder) {
        return builder.location(this);
    }

    @Override public String describe(FenceDescriptor descriptor) {
        return descriptor.location(this);
    }

    @Override public List<Integer> giveIcon(FenceIconGiver iconGiver) {
        return iconGiver.location(this);
    }

    public State getState() {
        return state;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRadius() {
        return radius;
    }


    public void setState(State state) {
        this.state = state;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setDwellTimeMillis(long dwellTimeMillis) {
        this.dwellTimeMillis = dwellTimeMillis;
    }

    public long getDwellTimeMillis() {
        return dwellTimeMillis;
    }

    @LocationName public String getName() {
        return name;
    }

    public void setName(@LocationName String name) {
        this.name = name;
    }
}
