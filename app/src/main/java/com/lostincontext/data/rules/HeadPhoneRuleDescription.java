package com.lostincontext.data.rules;


public class HeadPhoneRuleDescription implements RuleDescription {

    private State state;

    private HeadPhoneRuleDescription() { }

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

    public void setState(State state) {
        this.state = state;
    }
}
