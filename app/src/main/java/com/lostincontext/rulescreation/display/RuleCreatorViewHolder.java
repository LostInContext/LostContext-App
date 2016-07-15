package com.lostincontext.rulescreation.display;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lostincontext.R;
import com.lostincontext.data.FenceCreator;
import com.lostincontext.databinding.ItemSectionItemRuleCreationBinding;


public class RuleCreatorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private static final float SCALE_SELECTED = 1.1f;

    private final int elevationTarget;
    @ColorInt private int selectedColor;
    @ColorInt private final int defaultColor;

    public interface RuleCreationItemCallback {
        void onRuleCreationItemClick(FenceCreator fence);
    }

    private FenceCreator fenceCreator;
    private RuleCreationItemCallback callback;


    private ItemSectionItemRuleCreationBinding binding;

    public RuleCreatorViewHolder(ItemSectionItemRuleCreationBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        View root = binding.getRoot();
        root.setOnClickListener(this);

        Context context = root.getContext();
        elevationTarget = context.getResources().getDimensionPixelSize(R.dimen.lost_radio_button_elevation);


        defaultColor = ContextCompat.getColor(context, R.color.lost_radio_button_color);


    }

    public void setContent(FenceCreator fenceCreator,
                           RuleCreationItemCallback callback) {

        this.fenceCreator = fenceCreator;
        this.callback = callback;
        binding.setCallback(callback);
        binding.setItem(fenceCreator);
        selectedColor = fenceCreator.selectedColor;
        setState(fenceCreator.selected);


    }

    private void setState(boolean selected) {
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

    private void toggle() {
        if (fenceCreator.selected) setUnSelected();
        else setSelected();
        fenceCreator.selected = !fenceCreator.selected;
        if (callback != null) callback.onRuleCreationItemClick(fenceCreator);
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
