package com.lostincontext.data.rules;


import com.google.android.gms.awareness.fence.AwarenessFence;

import java.util.List;

public class DetectedActivityFenceVM implements FenceVM {

    private Type type;
    private State state;

    private DetectedActivityFenceVM() { }

    public DetectedActivityFenceVM(Type type, State state) {
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

    @Override public List<Integer> giveIcon(FenceIconGiver iconGiver) {
        return iconGiver.detectedActivity(this);
    }

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

    public Type getType() {
        return type;
    }

    public State getState() {
        return state;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setState(State state) {
        this.state = state;
    }
}
