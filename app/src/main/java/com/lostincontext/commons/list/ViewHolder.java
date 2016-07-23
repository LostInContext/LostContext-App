package com.lostincontext.commons.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lostincontext.BuildConfig;

public abstract class ViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = ViewHolder.class.getSimpleName();

    public ViewHolder(View itemView) {
        super(itemView);
    }


    /**
     * @return {@code true} if you have been able to rid the View of its transient animations, false otherwise.<br>
     * It is strongly encouraged to clear the view of its animations, this way it can be reused. Otherwise, RecyclerView has to create a new child view.
     * @see RecyclerView.Adapter#onFailedToRecycleView(RecyclerView.ViewHolder)
     */
    public boolean onFailedToRecycleView() {
        Log.w(TAG, "onFailedToRecycleItem" + toString());
        if (BuildConfig.DEBUG) {
            throw new RuntimeException("you need to rid the ViewHolder of its transient animations");
        }
        return false;
    }


}
