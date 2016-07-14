package com.lostincontext.commons.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.lostincontext.R;
import com.lostincontext.databinding.WidgetLostRadioButtonBinding;

import static android.support.v4.content.ContextCompat.getDrawable;


public class LostRadioButton extends RelativeLayout implements View.OnClickListener {

    private AnimatedVectorDrawable tickToPlus;
    private AnimatedVectorDrawable plusToTick;

    private boolean tick;

    private final int elevationTarget;


    @DrawableRes private int drawableRes;
    private WidgetLostRadioButtonBinding binding;

    public LostRadioButton(Context context) {
        this(context, null);
    }

    public LostRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LostRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LostRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater factory = LayoutInflater.from(context);

        binding = WidgetLostRadioButtonBinding.inflate(factory, this, true);
        setOnClickListener(this);

        tickToPlus = (AnimatedVectorDrawable) getDrawable(getContext(), R.drawable.avd_tick_to_plus);
        plusToTick = (AnimatedVectorDrawable) getDrawable(getContext(), R.drawable.avd_plus_to_tick);

        elevationTarget = getResources().getDimensionPixelSize(R.dimen.lost_radio_button_elevation);


        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LostRadioButton,
                0, 0);

        int res = a.getResourceId(R.styleable.LostRadioButton_src, -1);
        if (res != -1) binding.imageView.setImageResource(res);

        a.recycle();


    }

    private void tick() {
        if (tick) setPlus();
        else setTick();
        tick = !tick;
    }


    private void setTick() {
        AnimatedVectorDrawable drawable = plusToTick;
        binding.tick.setImageDrawable(drawable);
        binding.imageView.animate().scaleX(1.2f).scaleY(1.2f).z(elevationTarget);
        binding.tick.animate().z(elevationTarget);
        drawable.start();
    }


    private void setPlus() {
        AnimatedVectorDrawable drawable = tickToPlus;
        binding.tick.setImageDrawable(drawable);
        binding.imageView.animate().scaleX(1f).scaleY(1f).z(0f);
        binding.tick.animate().z(0f);
        drawable.start();
    }


    @Override public void onClick(View view) {
        tick();
    }
}
