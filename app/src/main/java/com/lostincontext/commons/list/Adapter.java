package com.lostincontext.commons.list;


import android.support.v7.widget.RecyclerView;

public abstract class Adapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {

    @Override public boolean onFailedToRecycleView(VH holder) {
        return holder.onFailedToRecycleView();
    }
}
