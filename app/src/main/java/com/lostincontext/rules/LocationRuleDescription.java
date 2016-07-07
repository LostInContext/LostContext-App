package com.lostincontext.rules;

import com.google.gson.Gson;
import com.lostincontext.model.DataSerializer;

/**
 * Created by syrinetrabelsi on 06/07/2016.
 */

public class LocationRuleDescription implements RuleDescription {
    private final State state;
    private final double latitude;
    private final double longitude;
    private final double radius;

    // TODO: 06/07/2016  make default value
    private long dwellTimeMillis;


    public LocationRuleDescription(State state, double latitude, double longitude, double radius) {
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    @Override public String serialize(Gson gson) {
        return gson.toJson(this);
    }

    @Override public LocationRuleDescription deserialize(Gson gson, String json) {
        return gson.fromJson(json, LocationRuleDescription.class);
    }

    public enum State {
        IN,
        ENTERING,
        EXITING
    }

    @Override public Rule visit(RuleBuilder builder) {
        return builder.buildLocationRule(this);
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
}
