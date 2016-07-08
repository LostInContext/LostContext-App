package com.lostincontext.model.rules;

import com.google.gson.Gson;
import com.lostincontext.model.DataSerializer;

/**
 * Created by syrinetrabelsi on 05/07/2016.
 */

public class DetectedActivityRuleDescription implements RuleDescription {
    private final Type type;
    private final State state;

    public DetectedActivityRuleDescription(Type type, State state) {
        this.type = type;
        this.state = state;
    }

    @Override
    public Rule visit(RuleBuilder builder) {
        return builder.buildDetectedActivityRule(this);
    }

    @Override public String serialize(Gson gson) {
        return gson.toJson(this);
    }

    @Override public DetectedActivityRuleDescription deserialize(Gson gson, String json) {
        return gson.fromJson(json, DetectedActivityRuleDescription.class);
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
}
