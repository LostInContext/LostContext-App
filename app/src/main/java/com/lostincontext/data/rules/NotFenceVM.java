package com.lostincontext.data.rules;


import com.google.android.gms.awareness.fence.AwarenessFence;

import java.util.List;

public class NotFenceVM implements FenceVM {

    private FenceVM fenceVM;

    private NotFenceVM() { }

    public NotFenceVM(FenceVM fenceVM) {
        this.fenceVM = fenceVM;
    }

    public FenceVM getFenceVM() {
        return fenceVM;
    }


    @Override public AwarenessFence build(FenceBuilder builder) {
        return builder.not(this);
    }

    @Override public String describe(FenceDescriptor descriptor) {
        return descriptor.not(this);
    }

    @Override public List<Integer> giveIcon(FenceIconGiver iconGiver) {
        return null;
    }

    public void setFenceVM(FenceVM fenceVM) {
        this.fenceVM = fenceVM;
    }
}
