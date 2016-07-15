package com.lostincontext.data.rules;

import com.google.android.gms.awareness.fence.AwarenessFence;

import java.util.List;

public class CompositeFenceVM implements FenceVM {

    public enum Operator {
        AND,
        OR
    }

    private List<FenceVM> fenceVMs;
    private Operator operator;

    private CompositeFenceVM() { }

    public CompositeFenceVM(List<FenceVM> fenceVMs, Operator operator) {
        this.fenceVMs = fenceVMs;
        this.operator = operator;
    }

    @Override
    public AwarenessFence build(FenceBuilder builder) {
        return builder.composite(this);
    }

    @Override public String describe(FenceDescriptor descriptor) {
        return descriptor.composite(this);
    }

    @Override public List<Integer> giveIcon(FenceIconGiver iconGiver) {
        return iconGiver.composite(this);
    }

    public List<FenceVM> getFenceVMs() {
        return fenceVMs;
    }


    public Operator getOperator() {
        return operator;
    }

    public void setFenceVMs(List<FenceVM> fenceVMs) {
        this.fenceVMs = fenceVMs;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
