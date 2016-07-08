package com.lostincontext.data.rules;

public class LocationRuleDescription implements RuleDescription {

    private State state;
    private double latitude;
    private double longitude;
    private double radius;

    // TODO: 06/07/2016  make default value
    private long dwellTimeMillis;

    private LocationRuleDescription() { }

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
