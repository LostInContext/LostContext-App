package com.lostincontext;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.HeadphoneFence;

/**
 * Created by syrinetrabelsi on 04/07/2016.
 */

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
