package com.lostincontext.data.rules;

import android.support.annotation.NonNull;

import com.lostincontext.R;

import java.util.List;

/**
 * Traverses the tree of fences in order to collect a list of icon representative of its detections
 */
public class FenceIconGiver {


    public void composite(CompositeFenceVM compositeFence, List<Integer> icons) {
        List<FenceVM> fenceVMs = compositeFence.getFenceVMs();
        for (FenceVM fenceVM : fenceVMs) {
            fenceVM.giveIcon(this, icons);
        }
    }

    public void location(LocationFenceVM fence, List<Integer> icons) {
        switch (fence.getName()) {
            case LocationFenceVM.HOME:
                icons.add(R.drawable.ic_home_24);
                break;
            case LocationFenceVM.WORK:
                icons.add(R.drawable.ic_work_24);
                break;

        }
    }

    @SuppressWarnings("UnusedParameters")
    public void headphone(@NonNull HeadphoneFenceVM fence, List<Integer> icons) {
        icons.add(R.drawable.ic_headset_24);
    }


    public void detectedActivity(DetectedActivityFenceVM fence, List<Integer> icons) {
        switch (fence.getType()) {
            case IN_VEHICLE:
                icons.add(R.drawable.ic_car_24);
                break;
            case RUNNING:
                icons.add(R.drawable.ic_run_24);
                break;
            case ON_BICYCLE:
                icons.add(R.drawable.ic_bike_24);
                break;
            case WALKING:
            default:
                icons.add(R.drawable.ic_walk_24);
                break;
        }
    }

}
