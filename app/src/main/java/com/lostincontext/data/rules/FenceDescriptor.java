package com.lostincontext.data.rules;

import android.text.TextUtils;

import java.util.List;

public class FenceDescriptor {

    public String not(NotFenceVM notFence) {
        String description = notFence.getFenceVM().describe(this);
        return "not { " + description + " } ";
    }

    public String composite(CompositeFenceVM compositeFence) {
        List<FenceVM> fenceVMs = compositeFence.getFenceVMs();
        String description = "";
        for (FenceVM fenceVM : fenceVMs) {
            if (!TextUtils.isEmpty(description)) description += getSeparator(compositeFence);
            description += fenceVM.describe(this);
        }
        return description;
    }

    private String getSeparator(CompositeFenceVM compositeFence) {
        switch (compositeFence.getOperator()) {

            case AND:
                return " and ";

            case OR:
            default:
                return " or ";
        }
    }

    public String location(LocationFenceVM fence) {
        //todo register name for location ?
        return "in " + fence.getLatitude() + " : " + fence.getLongitude();
    }


    public String time(TimeFenceVM fence) {
        return fence.getState().name();
    }

    public String headphone(HeadphoneFenceVM fence) {
        switch (fence.getState()) {

            case PLUGGED_IN:
                return " plugging in the headphones";

            case PLUGGED_OUT:
            default:
                return " unplugging the headphones";
        }


    }

    public String detectedActivity(DetectedActivityFenceVM fence) {

        String name = fence.getType().name();

        switch (fence.getState()) {

            case STARTING:
                return " starting " + name;
            case DURING:
                return " during " + name;
            case STOPPING:
            default:
                return " stopping " + name;
        }
    }
}
