package com.lostincontext.model.rules;

import com.google.gson.Gson;
import com.lostincontext.model.DataSerializer;

/**
 * Created by syrinetrabelsi on 05/07/2016.
 */

public class HeadPhoneRuleDescription implements RuleDescription {
    private final State state;

    public HeadPhoneRuleDescription(State state) {
        this.state = state;
    }

    @Override
    public Rule visit(RuleBuilder builder) {
        return builder.buildHeadPhoneRule(this);
    }

    @Override public String serialize(Gson gson) {
        return gson.toJson(this);
    }

    @Override public HeadPhoneRuleDescription deserialize(Gson gson, String json) {
        return gson.fromJson(json, HeadPhoneRuleDescription.class);
    }


    public enum State {
        PLUGGED_IN,
        PLUGGED_OUT
    }

    public State getState() {
        return state;
    }
}
