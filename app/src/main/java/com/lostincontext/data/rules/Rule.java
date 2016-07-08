package com.lostincontext.data.rules;

import com.google.android.gms.awareness.fence.AwarenessFence;


public class Rule {

    private final AwarenessFence fence;
    private final String name;


    public Rule(AwarenessFence fence, String name) {
        this.fence = fence;
        this.name = name;
    }

    public AwarenessFence getFence() {
        return fence;
    }

    public String getName() {
        return name;
    }
}
