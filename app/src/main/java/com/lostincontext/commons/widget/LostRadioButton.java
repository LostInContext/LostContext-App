package com.lostincontext.commons.widget;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.lostincontext.R;
import com.lostincontext.data.rules.FenceVM;
import com.lostincontext.databinding.WidgetLostRadioButtonBinding;


public class LostRadioButton extends RelativeLayout implements View.OnClickListener {


    public static final float SCALE_SELECTED = 1.2f;
    private FenceVM fence;
    private Callback callback;


    public interface Callback {
        void onLostRadioButtonClick(FenceVM fence, boolean selected);
    }

    private boolean selected;

    private final int elevationTarget;
    @ColorInt private final int selectedColor;
    @ColorInt private final int defaultColor;

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


        elevationTarget = getResources().getDimensionPixelSize(R.dimen.lost_radio_button_elevation);


        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LostRadioButton,
                0, 0);

        int res = a.getResourceId(R.styleable.LostRadioButton_src, -1);
        if (res != -1) binding.icon.setImageResource(res);
        res = a.getResourceId(R.styleable.LostRadioButton_selected_color, Color.RED);
        selectedColor = res;

        a.recycle();

        defaultColor = ContextCompat.getColor(context, R.color.lost_radio_button_color);

    }

    private void setState(boolean selected) {
        if (selected == this.selected) return;
        if (selected) {
            binding.icon.setScaleX(SCALE_SELECTED);
            binding.icon.setScaleY(SCALE_SELECTED);
            binding.icon.setElevation(elevationTarget);

        } else {
            binding.icon.setScaleX(0);
            binding.icon.setScaleY(0);
            binding.icon.setElevation(0);
        }
    }

    public void setContent(FenceVM fence,
                           Callback callback,
                           boolean selected) {
        this.fence = fence;
        this.callback = callback;
        setState(selected);
    }


    private void toggle() {
        if (selected) setUnSelected();
        else setSelected();
        selected = !selected;
        if (callback != null) callback.onLostRadioButtonClick(fence, selected);
    }

    private void setSelected() {
        animate(SCALE_SELECTED, elevationTarget, defaultColor, selectedColor);
    }


    private void setUnSelected() {
        animate(1f, 0f, selectedColor, defaultColor);
    }

    private void animate(float targetScale, float elevationTarget, int fromColor, int toColor) {
        binding.icon.animate()
                .scaleX(targetScale)
                .scaleY(targetScale)
                .z(elevationTarget);
        final GradientDrawable shapeDrawable = (GradientDrawable) binding.icon.getBackground();
        ValueAnimator textAnimator = ValueAnimator.ofArgb(fromColor,
                                                          toColor);
        textAnimator.setDuration(500);
        textAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (Integer) valueAnimator.getAnimatedValue();
                shapeDrawable.setColor(animatedValue);
            }
        });
        textAnimator.start();
    }


    @Override public void onClick(View view) {
        toggle();
    }
}
