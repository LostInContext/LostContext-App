package com.lostincontext.data.rules;

import com.lostincontext.R;

import java.util.ArrayList;
import java.util.List;

public class FenceIconGiver {


    public List<Integer> composite(CompositeFenceVM compositeFence) {
        List<FenceVM> fenceVMs = compositeFence.getFenceVMs();
        List<Integer> icons = new ArrayList<>();
        for (FenceVM fenceVM : fenceVMs) {
            if (fenceVM.giveIcon(this) != null) {
                icons.addAll(fenceVM.giveIcon(this));
            }
        }
        return icons;
    }

    public List<Integer> location(LocationFenceVM fence) {
        List<Integer> iconList = new ArrayList<>();
        switch (fence.getName()) {
            case LocationFenceVM.HOME:
                iconList.add(R.drawable.ic_home_24);
                break;
            case LocationFenceVM.WORK:
                iconList.add(R.drawable.ic_work_24);
                break;

        }
        return iconList;
    }

    public List<Integer> headphone(HeadphoneFenceVM fence) {
        List<Integer> iconList = new ArrayList<>();
        iconList.add(R.drawable.ic_headset_24);
        return iconList;
    }


    public List<Integer> detectedActivity(DetectedActivityFenceVM fence) {
        List<Integer> iconList = new ArrayList<>();
        switch (fence.getType()) {
            case IN_VEHICLE:
                iconList.add(R.drawable.ic_car_24);
                break;
            case RUNNING:
                iconList.add(R.drawable.ic_run_24);
                break;
            case ON_BICYCLE:
                iconList.add(R.drawable.ic_bike_24);
                break;
            case WALKING:
            default:
                iconList.add(R.drawable.ic_walk_24);
                break;
        }
        return iconList;
    }

}
