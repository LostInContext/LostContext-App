package com.lostincontext.data.rules;


import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.awareness.fence.AwarenessFence;

import java.util.List;

public class DetectedActivityFenceVM implements FenceVM {

    public enum Type {
        RUNNING,
        WALKING,
        IN_VEHICLE,
        ON_BICYCLE,
        ON_FOOT,
    }

    public enum State {
        STARTING,
        DURING,
        STOPPING
    }


    private final @NonNull Type type;
    private final @NonNull State state;

    @JsonCreator
    public DetectedActivityFenceVM(@JsonProperty("type") @NonNull Type type,
                                   @JsonProperty("state") @NonNull State state) {
        this.type = type;
        this.state = state;
    }

    @Override
    public AwarenessFence build(FenceBuilder builder) {
        return builder.detectedActivity(this);
    }

    @Override public String describe(FenceDescriptor descriptor) {
        return descriptor.detectedActivity(this);
    }

    @Override public void giveIcon(FenceIconGiver iconGiver, List<Integer> icons) {
        iconGiver.detectedActivity(this, icons);
    }


    @NonNull public Type getType() {
        return type;
    }

    @NonNull public State getState() {
        return state;
    }

}
