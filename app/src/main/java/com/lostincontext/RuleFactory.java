package com.lostincontext;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.HeadphoneFence;

/**
 * Created by syrinetrabelsi on 04/07/2016.
 */

public class RuleFactory {

    public static Rule headPhone(){
        AwarenessFence fence = HeadphoneFence.pluggingIn();
        String name = "HeadPhone are plugged in";
        return new Rule(fence,name);
    }

    public static Rule duringRunning(){
        AwarenessFence fence =  DetectedActivityFence.during(DetectedActivityFence.RUNNING);
        String name = "I am running";
        return new Rule(fence,name);
    }


    public static Rule and(Rule rule1, Rule rule2){
        AwarenessFence fence = AwarenessFence.and(rule1.getFence(), rule2.getFence());
        String name = rule1.getName() + " and " + rule2.getName();
        return new Rule(fence,name);
    }
}
