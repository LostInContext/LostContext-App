package com.lostincontext.data.rules;


public class DetectedActivityRuleDescription implements RuleDescription {

    private Type type;
    private State state;

    private DetectedActivityRuleDescription() { }

    public DetectedActivityRuleDescription(Type type, State state) {
        this.type = type;
        this.state = state;
    }

    @Override
    public Rule visit(RuleBuilder builder) {
        return builder.buildDetectedActivityRule(this);
    }

    public enum Type {
        RUNNING,
        WALKING,
        IN_VEHICLE,
        ON_BICYCLE,
        ON_FOOT,
    }

    public enum State {
        STARTING,
        DURING,
        STOPPING
    }

    public Type getType() {
        return type;
    }

    public State getState() {
        return state;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setState(State state) {
        this.state = state;
    }
}
