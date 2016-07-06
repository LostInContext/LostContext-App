package com.lostincontext.rules;

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

    public enum State {
        IN,
        ENTERING,
        EXITING
    }

    @Override public Rule visit(RuleBuilder builder) {
        return builder.buildRule(this);
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
