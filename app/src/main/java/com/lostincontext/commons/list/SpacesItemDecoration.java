package com.lostincontext.commons.list;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private final int span;

    public SpacesItemDecoration(int space,
                                int span) {
        this.space = space / 2;
        this.span = span;
    }

    @Override
    public void getItemOffsets(Rect outRect,
                               View view,
                               RecyclerView parent,
                               RecyclerView.State state) {


        int position = parent.getChildAdapterPosition(view);
        int column = position % span;

        if (position >= span) {
            outRect.top = space;
        }

        outRect.left = column * space / span;
        outRect.right = space - (column + 1) * space / span;


    }

    private static final String TAG = SpacesItemDecoration.class.getSimpleName();
}