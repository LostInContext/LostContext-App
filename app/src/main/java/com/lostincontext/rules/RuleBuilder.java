package com.lostincontext.rules;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.HeadphoneFence;
import com.google.android.gms.awareness.fence.LocationFence;
import com.google.android.gms.awareness.fence.TimeFence;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;
import java.util.TimeZone;

import static com.lostincontext.rules.TimeRuleDescription.State.IN_INTERVAL;

public class RuleBuilder {


    public Rule buildRule(RuleDescription ruleDescription) {
        return ruleDescription.visit(this);
    }

    public Rule buildLocationRule(LocationRuleDescription locationRuleDescription) {
        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        // return TODO;
        //}

        double latitude = locationRuleDescription.getLatitude();
        double longitude = locationRuleDescription.getLongitude();
        double radius = locationRuleDescription.getRadius();
        long dwellTimeMillis = locationRuleDescription.getDwellTimeMillis();
        LocationRuleDescription.State state = locationRuleDescription.getState();
        String name = state.name();

        switch (state) {

            case IN:

                return new Rule(LocationFence.in(latitude, longitude, radius, dwellTimeMillis), name);
            case ENTERING:
                return new Rule(LocationFence.entering(latitude, longitude, radius), name);
            case EXITING:
                return new Rule(LocationFence.exiting(latitude, longitude, radius), name);
        }
        throw new RuntimeException("Unknown location state");

    }

    public Rule buildTimeRule(TimeRuleDescription timeRuleDescription) {
        long startingTime = timeRuleDescription.getStarting();
        long endingTime = timeRuleDescription.getEnding();
        String state = timeRuleDescription.getState().name();

        switch (timeRuleDescription.getState()) {
            case IN_INTERVAL:
                return new Rule(TimeFence.inInterval(startingTime, endingTime), IN_INTERVAL.name());
            case IN_DAILY_INTERVAL:
                return new Rule(TimeFence.inDailyInterval(TimeZone.getDefault(), startingTime, endingTime), state);
            case IN_SUNDAY_INTERVAL:
                return new Rule(TimeFence.inSundayInterval(TimeZone.getDefault(), startingTime, endingTime), state);
            case IN_MONDAY_INTERVAL:
                return new Rule(TimeFence.inMondayInterval(TimeZone.getDefault(), startingTime, endingTime), state);
            case IN_TUESDAY_INTERVAL:
                return new Rule(TimeFence.inTuesdayInterval(TimeZone.getDefault(), startingTime, endingTime), state);
            case IN_WEDNESDAY_INTERVAL:
                return new Rule(TimeFence.inWednesdayInterval(TimeZone.getDefault(), startingTime, endingTime), state);
            case IN_THURSDAY_INTERVAL:
                return new Rule(TimeFence.inThursdayInterval(TimeZone.getDefault(), startingTime, endingTime), state);
            case IN_FRIDAY_INTERVAL:
                return new Rule(TimeFence.inFridayInterval(TimeZone.getDefault(), startingTime, endingTime), state);
            case IN_SATURDAY_INTERVAL:
                return new Rule(TimeFence.inSaturdayInterval(TimeZone.getDefault(), startingTime, endingTime), state);
        }
        throw new RuntimeException("Unknown time state");
    }

    public Rule buildHeadPhoneRule(HeadPhoneRuleDescription headPhoneRuleDescription) {
        switch (headPhoneRuleDescription.getState()) {
            case PLUGGED_IN:
                return new Rule(HeadphoneFence.pluggingIn(), "HeadPhone are plugged in");
            case PLUGGED_OUT:
                return new Rule(HeadphoneFence.unplugging(), "HeadPhone are not plugged in");
        }
        throw new RuntimeException("Unknown headphone state");
    }

    public Rule buildDetectedActivityRule(DetectedActivityRuleDescription detectedActivityRuleDescription) {
        int type;
        switch (detectedActivityRuleDescription.getType()) {
            case WALKING:
                type = DetectedActivity.WALKING;
                break;
            case IN_VEHICLE:
                type = DetectedActivity.IN_VEHICLE;
                break;
            case ON_BICYCLE:
                type = DetectedActivity.ON_BICYCLE;
                break;
            case ON_FOOT:
                type = DetectedActivity.ON_FOOT;
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

    public Rule buildCompositeRule(CompositeRuleDescription compositeRuleDescription) {
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


    public Rule and(Rule rule1, Rule rule2) {
        AwarenessFence fence = AwarenessFence.and(rule1.getFence(), rule2.getFence());
        String name = rule1.getName() + " and " + rule2.getName();
        return new Rule(fence, name);
    }

    public Rule or(Rule rule1, Rule rule2) {
        AwarenessFence fence = AwarenessFence.or(rule1.getFence(), rule2.getFence());
        String name = rule1.getName() + " or " + rule2.getName();
        return new Rule(fence, name);
    }

    public Rule not(Rule rule) {
        AwarenessFence fence = AwarenessFence.not(rule.getFence());
        String name = "not " + rule.getName();
        return new Rule(fence, name);
    }
}
