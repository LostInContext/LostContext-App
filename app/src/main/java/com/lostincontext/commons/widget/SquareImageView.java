package com.lostincontext.commons.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.lostincontext.R;

public class SquareImageView extends AppCompatImageView {

    private boolean mWidthFixed = true;

    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    protected void init(Context context, AttributeSet attributeSet, int defStyle) {
        if (attributeSet != null) {
            TypedArray a = context.obtainStyledAttributes(attributeSet,
                                                          R.styleable.SquareImageView,
                                                          defStyle,
                                                          0);
            mWidthFixed = a.getInt(R.styleable.SquareImageView_fixedSize, 0) == 0;
            a.recycle();
        }
    }

    // warning suppressed since we do want to pass the same argument twice
    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = mWidthFixed ? widthMeasureSpec : heightMeasureSpec;
        super.onMeasure(size, size);
    }
}