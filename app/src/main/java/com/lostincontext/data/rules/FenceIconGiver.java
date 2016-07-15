package com.lostincontext.data.rules;

import com.lostincontext.R;

import java.util.ArrayList;
import java.util.List;

public class FenceIconGiver {


    public List<Integer> composite(CompositeFenceVM compositeFence) {
        List<FenceVM> fenceVMs = compositeFence.getFenceVMs();
        List<Integer> icons = new ArrayList<>();
        for (FenceVM fenceVM : fenceVMs) {
            icons.addAll(fenceVM.giveIcon(this));
        }
        return icons;
    }

    public List<Integer> location(LocationFenceVM fence) {
        List<Integer> iconList = new ArrayList<>();
        switch (fence.getName()) {
            case LocationFenceVM.HOME:
                iconList.add(R.drawable.ic_home_24);
            case LocationFenceVM.WORK:
                iconList.add(R.drawable.ic_work_24);

        }
        return iconList;
    }

    public List<Integer> headphone(HeadphoneFenceVM fence) {
        List<Integer> iconList = new ArrayList<>();
        iconList.add(R.drawable.ic_deezer_logo_24);
        return iconList;
    }


    public List<Integer> detectedActivity(DetectedActivityFenceVM fence) {
        List<Integer> iconList = new ArrayList<>();
        switch (fence.getType()) {
            case IN_VEHICLE:
                iconList.add(R.drawable.ic_add_24);
            case RUNNING:
                iconList.add(R.drawable.ic_plus);
            case WALKING:
            default:
                iconList.add(R.drawable.ic_plus);
        }
        return iconList;
    }

}
