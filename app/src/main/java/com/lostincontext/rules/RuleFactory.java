package com.lostincontext.rules;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.HeadphoneFence;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

public class RuleFactory {


    public static Rule buildRule(RuleDescription ruleDescription) {

        if (ruleDescription instanceof CompositeRuleDescription) {
            final CompositeRuleDescription compositeRuleDescription = (CompositeRuleDescription) ruleDescription;
            return buildCompositeRule(compositeRuleDescription);
        }
        if (ruleDescription instanceof NotRuleDescription) {
            final NotRuleDescription notRuleDescription = (NotRuleDescription) ruleDescription;
            return not(buildRule(notRuleDescription.getRule()));
        }
        if (ruleDescription instanceof HeadPhoneRuleDescription) {
            final HeadPhoneRuleDescription headPhoneRuleDescription = (HeadPhoneRuleDescription) ruleDescription;
            return buildHeadPhoneRule(headPhoneRuleDescription);

        }
        if (ruleDescription instanceof DetectedActivityRuleDescription) {
            final DetectedActivityRuleDescription detectedActivityRuleDescription = (DetectedActivityRuleDescription) ruleDescription;
            return buildDetectedActivityRule(detectedActivityRuleDescription);

        }
        throw new RuntimeException("Unknown ruleDescription");
    }

    private static Rule buildHeadPhoneRule(HeadPhoneRuleDescription headPhoneRuleDescription) {
        switch (headPhoneRuleDescription.getState()) {
            case PLUGGED_IN:
                return new Rule(HeadphoneFence.pluggingIn(), "HeadPhone are plugged in");
            case PLUGGED_OUT:
                return new Rule(HeadphoneFence.unplugging(), "HeadPhone are not plugged in");
        }
        throw new RuntimeException("Unknown headphone state");
    }

    private static Rule buildDetectedActivityRule(DetectedActivityRuleDescription detectedActivityRuleDescription) {
        int type;
        switch (detectedActivityRuleDescription.getType()) {
            case WALKING:
                type = DetectedActivity.WALKING;
                break;
            case RUNNING:
            default:
                type = DetectedActivity.RUNNING;
                break;

        }
        switch (detectedActivityRuleDescription.getState()) {
            case STARTING:
                return new Rule(DetectedActivityFence.starting(type), "starting " + type);
            case DURING:
                return new Rule(DetectedActivityFence.during(type), "during " + type);
            case STOPPING:
                return new Rule(DetectedActivityFence.stopping(type), "stopping " + type);
        }
        throw new RuntimeException("Unknown DetectedActivity state");
    }

    private static Rule buildCompositeRule(CompositeRuleDescription compositeRuleDescription) {
        Rule resultRule;
        List<RuleDescription> ruleDescriptions = compositeRuleDescription.getRuleDescriptions();
        resultRule = buildRule(ruleDescriptions.get(0));
        for (int i = 1; i < ruleDescriptions.size(); i++) {
            switch (compositeRuleDescription.getOperator()) {

                case AND:
                    resultRule = and(resultRule, buildRule(ruleDescriptions.get(i)));
                    break;
                case OR:
                    resultRule = or(resultRule, buildRule(ruleDescriptions.get(i)));
                    break;
            }
        }
        return resultRule;
    }


    public static Rule and(Rule rule1, Rule rule2) {
        AwarenessFence fence = AwarenessFence.and(rule1.getFence(), rule2.getFence());
        String name = rule1.getName() + " and " + rule2.getName();
        return new Rule(fence, name);
    }

    public static Rule or(Rule rule1, Rule rule2) {
        AwarenessFence fence = AwarenessFence.or(rule1.getFence(), rule2.getFence());
        String name = rule1.getName() + " or " + rule2.getName();
        return new Rule(fence, name);
    }

    public static Rule not(Rule rule) {
        AwarenessFence fence = AwarenessFence.not(rule.getFence());
        String name = "not " + rule.getName();
        return new Rule(fence, name);
    }
}
