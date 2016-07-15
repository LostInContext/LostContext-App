package com.lostincontext.data.rules;


import com.google.android.gms.awareness.fence.AwarenessFence;

import java.util.List;

public class TimeFenceVM implements FenceVM {
    private State state;
    private long starting;
    private long ending;

    private TimeFenceVM() { }

    public TimeFenceVM(State state, long starting, long ending) {
        this.state = state;
        this.starting = starting;
        this.ending = ending;
    }


    @Override public AwarenessFence build(FenceBuilder builder) {
        return builder.time(this);
    }

    @Override public String describe(FenceDescriptor descriptor) {
        return descriptor.time(this);
    }

    @Override public List<Integer> giveIcon(FenceIconGiver iconGiver) {
        return null;
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
