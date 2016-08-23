package com.lostincontext.data.rules;

import android.support.annotation.NonNull;

import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.HeadphoneFence;
import com.google.android.gms.awareness.fence.TimeFence;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;
import java.util.TimeZone;

import static com.google.android.gms.awareness.fence.LocationFence.entering;
import static com.google.android.gms.awareness.fence.LocationFence.exiting;
import static com.google.android.gms.awareness.fence.LocationFence.in;

/**
 * Visitor who traverses the tree of fences in order to build the corresponding collective
 * {@link AwarenessFence}
 */
public class FenceBuilder {


    public AwarenessFence not(@NonNull NotFenceVM notFence) {
        AwarenessFence awarenessFence = notFence.getFenceVM().build(this);
        return AwarenessFence.not(awarenessFence);
    }

    public AwarenessFence composite(@NonNull CompositeFenceVM compositeFence) {
        List<FenceVM> fenceVMs = compositeFence.getFenceVMs();

        AwarenessFence[] awarenessFences = new AwarenessFence[fenceVMs.size()];

        for (int i = 0, count = fenceVMs.size(); i < count; i++) {
            awarenessFences[i] = fenceVMs.get(i).build(this);
        }

        switch (compositeFence.getOperator()) {

            case AND:
                return AwarenessFence.and(awarenessFences);

            case OR:
            default:
                return AwarenessFence.or(awarenessFences);
        }
    }

    @SuppressWarnings("MissingPermission")
    /**
     * Creates a location fence without checking the location permission
     * The permission is checked when the user picks a {@link LocationFenceVM} so it should be enough
     * in most cases
     */
    public AwarenessFence location(@NonNull LocationFenceVM fence) {
        double latitude = fence.getLatitude();
        double longitude = fence.getLongitude();
        double radius = fence.getRadius();
        long dwellTimeMillis = fence.getDwellTimeMillis();

        switch (fence.getState()) {

            case IN:
                return in(latitude, longitude, radius, dwellTimeMillis);

            case ENTERING:
                return entering(latitude, longitude, radius);

            case EXITING:
            default:
                return exiting(latitude, longitude, radius);
        }
    }

    public AwarenessFence time(@NonNull TimeFenceVM fence) {
        long startingTime = fence.getStarting();
        long endingTime = fence.getEnding();
        TimeFenceVM.State state = fence.getState();

        switch (state) {

            case IN_INTERVAL:
                return TimeFence.inInterval(startingTime, endingTime);
            case IN_DAILY_INTERVAL:
                return TimeFence.inDailyInterval(TimeZone.getDefault(), startingTime, endingTime);
            case IN_SUNDAY_INTERVAL:
                return TimeFence.inSundayInterval(TimeZone.getDefault(), startingTime, endingTime);
            case IN_MONDAY_INTERVAL:
                return TimeFence.inMondayInterval(TimeZone.getDefault(), startingTime, endingTime);
            case IN_TUESDAY_INTERVAL:
                return TimeFence.inTuesdayInterval(TimeZone.getDefault(), startingTime, endingTime);
            case IN_WEDNESDAY_INTERVAL:
                return TimeFence.inWednesdayInterval(TimeZone.getDefault(), startingTime, endingTime);
            case IN_THURSDAY_INTERVAL:
                return TimeFence.inThursdayInterval(TimeZone.getDefault(), startingTime, endingTime);
            case IN_FRIDAY_INTERVAL:
                return TimeFence.inFridayInterval(TimeZone.getDefault(), startingTime, endingTime);
            case IN_SATURDAY_INTERVAL:
            default:
                return TimeFence.inSaturdayInterval(TimeZone.getDefault(), startingTime, endingTime);
        }
    }

    public AwarenessFence headphone(@NonNull HeadphoneFenceVM fence) {
        switch (fence.getState()) {

            case PLUGGED_IN:
                return HeadphoneFence.pluggingIn();

            case PLUGGED_OUT:
            default:
                return HeadphoneFence.unplugging();
        }


    }

    public AwarenessFence detectedActivity(@NonNull DetectedActivityFenceVM fence) {
        int type;
        switch (fence.getType()) {
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

        switch (fence.getState()) {

            case STARTING:
                return DetectedActivityFence.starting(type);
            case DURING:
                return DetectedActivityFence.during(type);
            case STOPPING:
            default:
                return DetectedActivityFence.stopping(type);
        }
    }

}
