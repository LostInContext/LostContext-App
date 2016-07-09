package com.lostincontext.data.rules;


public class HeadphoneRuleDescription implements RuleDescription {

    private State state;

    private HeadphoneRuleDescription() { }

    public HeadphoneRuleDescription(State state) {
        this.state = state;
    }

    @Override
    public Rule visit(RuleBuilder builder) {
        return builder.buildHeadphoneRule(this);
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
