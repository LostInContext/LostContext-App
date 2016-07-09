package com.lostincontext.data.rules;

import com.google.android.gms.awareness.fence.AwarenessFence;

public class LocationFenceVM implements FenceVM {

    public enum State {
        IN,
        ENTERING,
        EXITING
    }

    private State state;
    private double latitude;
    private double longitude;
    private double radius;

    // TODO: 06/07/2016  make default value
    private long dwellTimeMillis;

    private LocationFenceVM() { }

    public LocationFenceVM(State state, double latitude, double longitude, double radius) {
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }



    @Override public AwarenessFence build(FenceBuilder builder) {
        return builder.location(this);
    }

    @Override public String describe(FenceDescriptor descriptor) {
        return descriptor.location(this);
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

    public long getDwellTimeMillis() {
        return dwellTimeMillis;
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
}
