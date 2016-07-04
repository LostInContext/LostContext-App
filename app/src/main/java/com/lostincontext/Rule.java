package com.lostincontext;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.HeadphoneFence;

/**
 * Created by STrabelsi on 04/07/2016.
 */

public class Rule {

    AwarenessFence fence;
    Object playlist;
    String name;

    public Rule(AwarenessFence fence, Object playlist, String name) {
        this.fence = fence;
        this.playlist = playlist;
        this.name = name;

        AwarenessFence.and(HeadphoneFence.pluggingIn(), DetectedActivityFence.during(0));
    }
}
