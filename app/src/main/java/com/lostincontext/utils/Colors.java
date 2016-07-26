package com.lostincontext.utils;


import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class Colors {


    public static void animateBackgroundColor(final View view,
                                              final @ColorInt int from,
                                              final @ColorInt int to,
                                              long duration,
                                              @Nullable Interpolator interpolator) {

        ValueAnimator valueAnimator = ValueAnimator.ofArgb(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setBackgroundColor((Integer) valueAnimator.getAnimatedValue());
            }
        });
        startAnimateColor(valueAnimator, duration, interpolator);
    }


    public static void animateTextColor(final TextView textView,
                                        final @ColorInt int from,
                                        final @ColorInt int to,
                                        long duration,
                                        @Nullable Interpolator interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofArgb(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textView.setTextColor((Integer) valueAnimator.getAnimatedValue());
            }
        });
        startAnimateColor(valueAnimator, duration, interpolator);
    }

    public static void animateImageTint(final ImageView imageView,
                                        final @ColorInt int from,
                                        final @ColorInt int to,
                                        long duration,
                                        @Nullable Interpolator interpolator) {

        ValueAnimator valueAnimator = ValueAnimator.ofArgb(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                imageView.setImageTintList(ColorStateList.valueOf((Integer) valueAnimator.getAnimatedValue()));
            }
        });
        startAnimateColor(valueAnimator, duration, interpolator);
    }

    public static void animateDrawableTint(final Drawable drawable,
                                           final @ColorInt int from,
                                           final @ColorInt int to,
                                           long duration,
                                           @Nullable Interpolator interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofArgb(from, to);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                drawable.setTint((Integer) valueAnimator.getAnimatedValue());
            }
        });
        startAnimateColor(valueAnimator, duration, interpolator);
    }

    private static void startAnimateColor(ValueAnimator valueAnimator,
                                          long duration,
                                          @Nullable Interpolator interpolator) {
        if (interpolator != null) valueAnimator.setInterpolator(interpolator);
        valueAnimator.setDuration(duration).start();
    }


    private static int blendColors(@ColorInt int from,
                                   @ColorInt int to,
                                   float ratio) {
        float inverseRatio = 1f - ratio;

        int r = (int) (Color.red(to) * ratio + Color.red(from) * inverseRatio);
        int g = (int) (Color.green(to) * ratio + Color.green(from) * inverseRatio);
        int b = (int) (Color.blue(to) * ratio + Color.blue(from) * inverseRatio);

        return Color.rgb(r, g, b);
    }
}
