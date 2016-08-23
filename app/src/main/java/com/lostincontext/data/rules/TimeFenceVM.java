package com.lostincontext.data.rules;


import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.awareness.fence.AwarenessFence;

import java.util.List;

public class TimeFenceVM implements FenceVM {
    private final @NonNull State state;
    private final long starting;
    private final long ending;

    @JsonCreator
    public TimeFenceVM(@NonNull @JsonProperty("state") State state,
                       @JsonProperty("starting") long starting,
                       @JsonProperty("ending") long ending) {
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

    @Override public void giveIcon(FenceIconGiver iconGiver, List<Integer> icons) { }

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

    @NonNull public State getState() {
        return state;
    }

    public long getStarting() {
        return starting;
    }

    public long getEnding() {
        return ending;
    }


}
