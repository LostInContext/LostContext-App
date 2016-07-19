package com.lostincontext.mainscreen;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Interpolator;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.Target;
import com.lostincontext.PlaylistLauncher;
import com.lostincontext.R;
import com.lostincontext.commons.images.palette.PaletteBitmap;
import com.lostincontext.commons.images.palette.PaletteBitmapTranscoder;
import com.lostincontext.commons.images.palette.PaletteImageViewTarget;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.data.rules.FenceIconGiver;
import com.lostincontext.data.rules.Rule;
import com.lostincontext.databinding.ItemRuleBinding;

import java.util.List;

public class MainScreenViewHolder extends RecyclerView.ViewHolder implements RequestListener<Playlist, PaletteBitmap> {

    public interface Callback {
        void onPlaylistCoverClick(Playlist playlist);
    }

    private final ItemRuleBinding binding;
    private final PaletteImageViewTarget target;
    private PaletteBitmapTranscoder transcoder;

    private final @ColorInt int defaultTextColor;
    private final @ColorInt int defaultBackgroundColor;
    private final @ColorInt int defaultIconColor;

    private final int animationDuration;
    private final Interpolator interpolator;

    ViewPropertyAnimation.Animator AlphaAnimator = new ViewPropertyAnimation.Animator() {
        @Override
        public void animate(View view) {
            AlphaAnimation animation = new AlphaAnimation(0f, 1f);
            animation.setDuration(animationDuration);
            animation.setInterpolator(interpolator);
            view.startAnimation(animation);
        }
    };

    public MainScreenViewHolder(final ItemRuleBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.target = new PaletteImageViewTarget(binding.image);
        this.defaultTextColor = binding.itemTitle.getCurrentTextColor();
        Context context = binding.getRoot().getContext();
        this.defaultBackgroundColor = ContextCompat.getColor(context,
                                                             R.color.playlist_text_default_background);
        this.defaultIconColor = ContextCompat.getColor(context,
                                                       R.color.playlist_icon_default_filter);

        this.transcoder = new PaletteBitmapTranscoder(context);
        this.animationDuration = context.getResources().getInteger(android.R.integer.config_longAnimTime);
        this.interpolator = new LinearOutSlowInInterpolator();
    }

    public void setContent(Rule rule) {

        binding.setRule(rule);

        binding.setCallback(new MainScreenViewHolder.Callback() {
            @Override public void onPlaylistCoverClick(Playlist playlist) {
                PlaylistLauncher launcher = new PlaylistLauncher();
                launcher.launchPlaylist(binding.getRoot().getContext(), playlist);
            }
        });

        binding.textBackground.setBackgroundColor(defaultBackgroundColor);
        binding.itemTitle.setTextColor(defaultTextColor);

        Glide.with(binding.getRoot().getContext())
                .load(rule.getPlaylist())
                .asBitmap()
                .transcode(transcoder, PaletteBitmap.class)
                .animate(AlphaAnimator)
                .listener(this)
                .into(target);

        List<Integer> icons = rule.getFenceVM().giveIcon(new FenceIconGiver());
        if (icons != null && !icons.isEmpty()) {
            if (icons.size() >= 3) {
                binding.ic3.setImageResource(icons.get(2));
            }
            if (icons.size() >= 2) {
                binding.ic2.setImageResource(icons.get(1));
            }
            binding.ic1.setImageResource(icons.get(0));

        }
    }

    //region requestListener
    @Override
    public boolean onException(Exception e, Playlist model, Target<PaletteBitmap> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(PaletteBitmap resource, Playlist model, Target<PaletteBitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
        applyPalette(resource, !isFromMemoryCache);
        return false;
    }
    //endregion requestListener

    private void applyPalette(PaletteBitmap resource, boolean shouldAnimate) {
        Palette palette = resource.palette;
        if (palette == null) return;
        Palette.Swatch swatch = getSwatch(palette);
        if (swatch == null) return;
        if (shouldAnimate) {
            ValueAnimator textAnimator = ValueAnimator.ofArgb(binding.itemTitle.getCurrentTextColor(),
                                                              swatch.getTitleTextColor());
            textAnimator.setDuration(animationDuration);
            textAnimator.setInterpolator(interpolator);
            textAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int animatedValue = (Integer) valueAnimator.getAnimatedValue();
                    binding.itemTitle.setTextColor(animatedValue);
                }
            });
            textAnimator.start();


            ValueAnimator iconAnimator = ValueAnimator.ofArgb(defaultIconColor,
                                                              swatch.getBodyTextColor());

            iconAnimator.setDuration(animationDuration);
            iconAnimator.setInterpolator(interpolator);
            iconAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {

                }
            });
            iconAnimator.start();

            ValueAnimator backgroundAnimator = ValueAnimator.ofArgb(defaultBackgroundColor,
                                                                    swatch.getRgb());
            backgroundAnimator.setDuration(animationDuration);
            backgroundAnimator.setInterpolator(interpolator);
            backgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    binding.textBackground.setBackgroundColor((Integer) valueAnimator.getAnimatedValue());
                }
            });
            backgroundAnimator.start();

        } else {
            binding.itemTitle.setTextColor(swatch.getTitleTextColor());
            binding.textBackground.setBackgroundColor(swatch.getRgb());
        }

    }

    @Nullable
    private static Palette.Swatch getSwatch(Palette palette) {
        Palette.Swatch swatch = palette.getVibrantSwatch();
        if (swatch == null) swatch = palette.getDarkVibrantSwatch();
        if (swatch == null) swatch = palette.getLightVibrantSwatch();
        if (swatch == null) {
            List<Palette.Swatch> swatches = palette.getSwatches();
            if (swatches.size() != 0) swatch = swatches.get(0);
        }
        return swatch;
    }
}
