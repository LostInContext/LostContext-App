package com.lostincontext.data.rules;


import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.awareness.fence.AwarenessFence;

import java.util.List;

public class HeadphoneFenceVM implements FenceVM {

    public enum State {
        PLUGGED_IN,
        PLUGGED_OUT
    }

    private final @NonNull State state;

    @JsonCreator
    public HeadphoneFenceVM(@JsonProperty("state") @NonNull State state) {
        this.state = state;
    }

    @Override
    public AwarenessFence build(FenceBuilder builder) {
        return builder.headphone(this);
    }

    @Override public String describe(FenceDescriptor descriptor) {
        return descriptor.headphone(this);
    }

    @Override public void giveIcon(FenceIconGiver iconGiver, List<Integer> icons) {
        iconGiver.headphone(this, icons);
    }

    @NonNull public State getState() {
        return state;
    }

}
