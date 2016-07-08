package com.lostincontext.model.rules;

/**
 * Created by STrabelsi on 06/07/2016.
 */

public class TimeRuleDescription extends RuleDescription {
    private State state;
    private long starting;
    private long ending;

    public TimeRuleDescription() {
    }

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

    public void setState(State state) {
        this.state = state;
    }

    public void setStarting(long starting) {
        this.starting = starting;
    }

    public void setEnding(long ending) {
        this.ending = ending;
    }
}
