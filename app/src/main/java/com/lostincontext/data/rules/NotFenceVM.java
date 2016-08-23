package com.lostincontext.data.rules;


import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.awareness.fence.AwarenessFence;

import java.util.List;

public class NotFenceVM implements FenceVM {

    private final @NonNull FenceVM fenceVM;

    @JsonCreator
    public NotFenceVM(@NonNull @JsonProperty("fenceVM") FenceVM fenceVM) {
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

    @Override public void giveIcon(FenceIconGiver iconGiver, List<Integer> icons) {

    }

}
