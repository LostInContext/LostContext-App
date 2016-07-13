package com.lostincontext.playlists;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Palette.Swatch;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.Target;
import com.lostincontext.PlaylistLauncher;
import com.lostincontext.R;
import com.lostincontext.commons.images.palette.PaletteBitmap;
import com.lostincontext.commons.images.palette.PaletteBitmapTranscoder;
import com.lostincontext.commons.images.palette.PaletteImageViewTarget;
import com.lostincontext.data.Playlist;
import com.lostincontext.databinding.ItemPlaylistBinding;

import java.util.List;

public class PlaylistViewHolder extends RecyclerView.ViewHolder implements RequestListener<Playlist, PaletteBitmap> {


    public interface Callback {
        void onDeezerLogoClick(Playlist playlist);
    }


    private final ItemPlaylistBinding binding;
    private final PaletteImageViewTarget target;
    private PaletteBitmapTranscoder transcoder;

    private final @ColorInt int defaultTextColor;
    private final @ColorInt int defaultBackgroundColor;

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

    public PlaylistViewHolder(final ItemPlaylistBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.target = new PaletteImageViewTarget(binding.image);
        this.defaultTextColor = binding.itemTitle.getCurrentTextColor();
        Context context = binding.getRoot().getContext();
        this.defaultBackgroundColor = ContextCompat.getColor(context,
                                                             R.color.playlist_text_default_background);
        this.transcoder = new PaletteBitmapTranscoder(context);
        this.animationDuration = context.getResources().getInteger(android.R.integer.config_longAnimTime);
        this.interpolator = new LinearOutSlowInInterpolator();
    }


    public void setContent(Playlist playlist) {

        binding.setPlaylist(playlist);

        binding.setCallback(new Callback() {
            @Override public void onDeezerLogoClick(Playlist playlist) {
                PlaylistLauncher launcher = new PlaylistLauncher();
                launcher.launchPlaylist(binding.getRoot().getContext(), playlist);
            }
        });

        binding.textBackground.setBackgroundColor(defaultBackgroundColor);
        binding.itemInfo.setTextColor(defaultTextColor);
        binding.itemTitle.setTextColor(defaultTextColor);
        binding.deezerLogo.setColorFilter(defaultTextColor);

        Glide.with(binding.getRoot().getContext())
                .load(playlist)
                .asBitmap()
                .transcode(transcoder, PaletteBitmap.class)
                .animate(AlphaAnimator)
                .listener(this)
                .into(target);

    }

    //region RequestListener

    @Override
    public boolean onException(Exception e,
                               Playlist model,
                               Target<PaletteBitmap> target,
                               boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(PaletteBitmap resource,
                                   Playlist model,
                                   Target<PaletteBitmap> target,
                                   boolean isFromMemoryCache,
                                   boolean isFirstResource) {
        applyPalette(resource, !isFromMemoryCache);
        return false;
    }

    //endregion


    private void applyPalette(PaletteBitmap resource, boolean shouldAnimate) {
        Palette palette = resource.palette;
        if (palette == null) return;
        Swatch swatch = getSwatch(palette);
        if (swatch == null) return;
        if (shouldAnimate) {
            ValueAnimator textAnimator = ValueAnimator.ofArgb(binding.itemTitle.getCurrentTextColor(),
                                                              swatch.getTitleTextColor());
            textAnimator.setDuration(animationDuration);
            textAnimator.setInterpolator(interpolator);
            textAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    binding.itemInfo.setTextColor((Integer) valueAnimator.getAnimatedValue());
                    binding.itemTitle.setTextColor((Integer) valueAnimator.getAnimatedValue());
                }
            });
            textAnimator.start();

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

            ImageView deezerLogo = binding.deezerLogo;

            ObjectAnimator color = ObjectAnimator.ofArgb(deezerLogo,
                                                         "colorFilter",
                                                         defaultTextColor,
                                                         swatch.getTitleTextColor());
            color.setDuration(animationDuration);
            color.start();

        } else {
            binding.itemInfo.setTextColor(swatch.getTitleTextColor());
            binding.itemTitle.setTextColor(swatch.getTitleTextColor());
            binding.textBackground.setBackgroundColor(swatch.getRgb());
            binding.deezerLogo.setColorFilter(swatch.getTitleTextColor());
        }

    }

    @Nullable
    private static Swatch getSwatch(Palette palette) {
        Swatch swatch = palette.getVibrantSwatch();
        if (swatch == null) swatch = palette.getDarkVibrantSwatch();
        if (swatch == null) swatch = palette.getLightVibrantSwatch();
        if (swatch == null) {
            List<Swatch> swatches = palette.getSwatches();
            if (swatches.size() != 0) swatch = swatches.get(0);
        }
        return swatch;
    }

}
