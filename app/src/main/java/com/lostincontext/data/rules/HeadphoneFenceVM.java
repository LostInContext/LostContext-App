package com.lostincontext.data.rules;


import com.google.android.gms.awareness.fence.AwarenessFence;

public class HeadphoneFenceVM implements FenceVM {

    public enum State {
        PLUGGED_IN,
        PLUGGED_OUT
    }

    private State state;

    private HeadphoneFenceVM() { }

    public HeadphoneFenceVM(State state) {
        this.state = state;
    }

    @Override
    public AwarenessFence build(FenceBuilder builder) {
        return builder.headphone(this);
    }

    @Override public String describe(FenceDescriptor descriptor) {
        return descriptor.headphone(this);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
