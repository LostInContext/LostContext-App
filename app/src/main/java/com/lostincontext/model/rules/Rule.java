package com.lostincontext.model.rules;

import com.google.android.gms.awareness.fence.AwarenessFence;


public class Rule {
    public Rule(AwarenessFence fence, String name) {
        this.fence = fence;
        this.name = name;
    }

    private AwarenessFence fence;
    private String name;


    public AwarenessFence getFence() {
        return fence;
    }

    public String getName() {
        return name;
    }
}
