package com.lostincontext.rules;

/**
 * Created by STrabelsi on 06/07/2016.
 */

public class TimeRuleDescription implements RuleDescription {
    private final State state;
    private final long starting;
    private final long ending;

    public TimeRuleDescription(State state, long starting, long ending) {
        this.state = state;
        this.starting = starting;
        this.ending = ending;
    }


    @Override public Rule visit(RuleBuilder builder) {
        return builder.buildTimeRule(this);
    }

    public enum State {
        IN_INTERVAL,
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

    public long getStarting() {
        return starting;
    }

    public long getEnding() {
        return ending;
    }
}
