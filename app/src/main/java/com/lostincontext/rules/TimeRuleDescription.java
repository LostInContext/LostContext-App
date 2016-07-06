package com.lostincontext.rules;

/**
 * Created by STrabelsi on 06/07/2016.
 */

public class TimeRuleDescription implements RuleDescription {
    private State state;

    public TimeRuleDescription(State state) {
        this.state = state;
    }

    @Override public Rule visit(RuleBuilder builder) {
        return builder.buildTimeRule(this);
    }

    public enum State {
        IN_DAILY_INTERVAL,
        IN_SUNDAY_INTERVAL,
        IN_MONDAY_INTERVAL,
        IN_TUESDAY_INTERVAL,
        IN_WEDNESDAY_INTERVAL,
        IN_THURSDAY_INTERVAL,
        IN_FRIDAY_INTERVAL,
        IN_SATURDAY_INTERVAL
    }

    public State getState() {
        return state;
    }
}
