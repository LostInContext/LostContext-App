package com.lostincontext.commons.list;


import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * ViewHolder without any callback, getters or setters. Does not respond to clicks either.
 */
public class DummyViewHolder extends RecyclerView.ViewHolder {

    public DummyViewHolder(View itemView) {
        super(itemView);
    }

}