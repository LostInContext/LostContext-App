package com.lostincontext.playlists;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.Target;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lostincontext.PlaylistLauncher;
import com.lostincontext.R;
import com.lostincontext.commons.images.palette.PaletteBitmap;
import com.lostincontext.commons.images.palette.PaletteBitmapTranscoder;
import com.lostincontext.commons.images.palette.PaletteImageViewTarget;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.data.playlist.PlaylistPicker;
import com.lostincontext.databinding.ItemPlaylistBinding;

import java.util.List;

public class PlaylistViewHolder extends RecyclerView.ViewHolder implements RequestListener<Playlist, PaletteBitmap> {


    public static final PorterDuff.Mode MODE = PorterDuff.Mode.SRC_ATOP;

    public interface Callback {
        void onDeezerLogoClick(Playlist playlist);

        void onItemClick(Playlist playlist);
    }


    private final ItemPlaylistBinding binding;
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

    public PlaylistViewHolder(final ItemPlaylistBinding binding) {
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


    public void setContent(Playlist playlist) {

        binding.setPlaylist(playlist);

        binding.setCallback(new Callback() {
            @Override public void onDeezerLogoClick(Playlist playlist) {
                PlaylistLauncher launcher = new PlaylistLauncher();
                launcher.launchPlaylist(binding.getRoot().getContext(), playlist);
            }

            @Override public void onItemClick(Playlist playlist) {

                try {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("playlist",new PlaylistPicker(playlist).serialize());
                    ((Activity)binding.getRoot().getContext()).setResult(Activity.RESULT_OK, returnIntent);
                    ((Activity)binding.getRoot().getContext()).finish();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        });

        binding.textBackground.setBackgroundColor(defaultBackgroundColor);
        binding.itemInfo.setTextColor(defaultTextColor);
        binding.itemTitle.setTextColor(defaultTextColor);
        binding.deezerLogo.setColorFilter(defaultIconColor, MODE);

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
                    int animatedValue = (Integer) valueAnimator.getAnimatedValue();
                    binding.itemInfo.setTextColor(animatedValue);
                    binding.itemTitle.setTextColor(animatedValue);
                }
            });
            textAnimator.start();


            ValueAnimator iconAnimator = ValueAnimator.ofArgb(defaultIconColor,
                                                              swatch.getTitleTextColor());

            iconAnimator.setDuration(animationDuration);
            iconAnimator.setInterpolator(interpolator);
            iconAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    binding.deezerLogo.setColorFilter((Integer) valueAnimator.getAnimatedValue(),
                                                      MODE);
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
            binding.itemInfo.setTextColor(swatch.getTitleTextColor());
            binding.itemTitle.setTextColor(swatch.getTitleTextColor());
            binding.textBackground.setBackgroundColor(swatch.getRgb());
            binding.deezerLogo.setColorFilter(swatch.getTitleTextColor(), MODE);
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
