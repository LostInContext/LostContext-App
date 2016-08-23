package com.lostincontext.data.rules;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.awareness.fence.AwarenessFence;

import java.util.List;

public class CompositeFenceVM implements FenceVM {

    public enum Operator {
        AND,
        OR
    }

    private final @NonNull List<FenceVM> fenceVMs;
    private final @NonNull Operator operator;

    @JsonCreator
    public CompositeFenceVM(@NonNull @JsonProperty("fenceVMs") List<FenceVM> fenceVMs,
                            @NonNull @JsonProperty("operator") Operator operator) {
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

    @Override public void giveIcon(FenceIconGiver iconGiver, List<Integer> icons) {
        iconGiver.composite(this, icons);
    }

    @NonNull public List<FenceVM> getFenceVMs() {
        return fenceVMs;
    }


    @NonNull public Operator getOperator() {
        return operator;
    }

}
