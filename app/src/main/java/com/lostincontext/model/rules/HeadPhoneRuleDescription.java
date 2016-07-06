package com.lostincontext.model.rules;

/**
 * Created by syrinetrabelsi on 05/07/2016.
 */

public class HeadPhoneRuleDescription implements RuleDescription {
    private State state;

    public HeadPhoneRuleDescription(State state) {
        this.state = state;
    }

    @Override
    public Rule visit(RuleBuilder builder) {
        return builder.buildHeadPhoneRule(this);
    }


    public enum State {
        PLUGGED_IN,
        PLUGGED_OUT
    }

    public State getState() {
        return state;
    }
}
