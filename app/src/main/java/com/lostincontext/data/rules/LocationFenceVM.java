package com.lostincontext.data.rules;

import android.support.annotation.StringDef;

import com.google.android.gms.awareness.fence.AwarenessFence;

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

    @LocationName public String getName() {
        return name;
    }

    public void setName(@LocationName String name) {
        this.name = name;
    }
}
