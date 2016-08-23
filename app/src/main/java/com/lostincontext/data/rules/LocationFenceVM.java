package com.lostincontext.data.rules;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    public static final String HOME = "HOME";
    public static final String WORK = "WORK";


    private final @NonNull State state;
    private final double latitude;
    private final double longitude;
    private final double radius;
    private final @NonNull @LocationName String name;

    private final static long DWELL_TIME_MILLIS = 10000; //10 seconds

    @SuppressWarnings("unused")
    @JsonCreator
    public LocationFenceVM(@JsonProperty("state") @NonNull State state,
                           @JsonProperty("latitude") double latitude,
                           @JsonProperty("longitude") double longitude,
                           @JsonProperty("radius") double radius,
                           @JsonProperty("name") @NonNull String name) {
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.name = name;
    }

    public LocationFenceVM(@NonNull @LocationName String locationName, LatLng latLng) {
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

    @Override public void giveIcon(FenceIconGiver iconGiver, List<Integer> icons) {
        iconGiver.location(this, icons);
    }

    @NonNull public State getState() {
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
        return DWELL_TIME_MILLIS;
    }

    @NonNull @LocationName public String getName() {
        return name;
    }

}
