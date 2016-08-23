package com.lostincontext.data.rules;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.List;

/**
 * Visitor who traverses the fence tree in order to create an human readable description.
 */
public class FenceDescriptor {

    public String not(@NonNull NotFenceVM notFence) {
        String description = notFence.getFenceVM().describe(this);
        return "not { " + description + " } ";
    }

    public String composite(@NonNull CompositeFenceVM compositeFence) {
        List<FenceVM> fenceVMs = compositeFence.getFenceVMs();
        String description = "";
        for (FenceVM fenceVM : fenceVMs) {
            if (!TextUtils.isEmpty(description)) description += getSeparator(compositeFence);
            description += fenceVM.describe(this);
        }
        return description;
    }

    private String getSeparator(@NonNull CompositeFenceVM compositeFence) {
        switch (compositeFence.getOperator()) {

            case AND:
                return " and ";

            case OR:
            default:
                return " or ";
        }
    }

    public String location(@NonNull LocationFenceVM fence) {
        return "at " + fence.getName();
    }


    public String time(@NonNull TimeFenceVM fence) {
        return fence.getState().name();
    }

    public String headphone(@NonNull HeadphoneFenceVM fence) {
        switch (fence.getState()) {

            case PLUGGED_IN:
                return " plugging in the headphones";

            case PLUGGED_OUT:
            default:
                return " unplugging the headphones";
        }


    }

    public String detectedActivity(@NonNull DetectedActivityFenceVM fence) {

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
