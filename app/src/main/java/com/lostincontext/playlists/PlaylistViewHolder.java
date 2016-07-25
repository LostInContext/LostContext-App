package com.lostincontext.playlists;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Palette.Swatch;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.lostincontext.commons.list.ViewHolder;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.data.playlist.PlaylistPicker;
import com.lostincontext.databinding.ItemPlaylistBinding;

import java.util.List;

import static android.support.v4.content.ContextCompat.getColor;
import static com.lostincontext.utils.Colors.animateBackgroundColor;
import static com.lostincontext.utils.Colors.animateImageTint;
import static com.lostincontext.utils.Colors.animateTextColor;

public class PlaylistViewHolder extends ViewHolder implements RequestListener<Playlist, PaletteBitmap> {


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

    ViewPropertyAnimation.Animator alphaAnimator = new ViewPropertyAnimation.Animator() {
        @Override
        public void animate(View view) {
            view.setAlpha(0f);
            view.animate()
                    .withLayer()
                    .alpha(1f)
                    .setDuration(animationDuration)
                    .setInterpolator(interpolator)
                    .start();
        }
    };

    public static PlaylistViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
        ItemPlaylistBinding binding = ItemPlaylistBinding.inflate(layoutInflater, parent, false);
        return new PlaylistViewHolder(binding);
    }

    public PlaylistViewHolder(final ItemPlaylistBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.target = new PaletteImageViewTarget(binding.image);
        this.defaultTextColor = binding.itemTitle.getCurrentTextColor();
        Context context = binding.getRoot().getContext();
        this.defaultBackgroundColor = getColor(context,
                                               R.color.playlist_text_default_background);
        this.defaultIconColor = getColor(context,
                                         R.color.playlist_icon_default_color);

        this.transcoder = new PaletteBitmapTranscoder(context);
        this.animationDuration = context.getResources().getInteger(android.R.integer.config_longAnimTime);
        this.interpolator = new LinearOutSlowInInterpolator();
    }


    public void bindTo(Playlist playlist) {

        binding.setPlaylist(playlist);

        binding.setCallback(new Callback() {
            @Override public void onDeezerLogoClick(Playlist playlist) {
                PlaylistLauncher launcher = new PlaylistLauncher();
                launcher.launchPlaylist(binding.getRoot().getContext(), playlist);
            }

            @Override public void onItemClick(Playlist playlist) {

                try {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("playlist", new PlaylistPicker(playlist).serialize());
                    ((Activity) binding.getRoot().getContext()).setResult(Activity.RESULT_OK, returnIntent);
                    ((Activity) binding.getRoot().getContext()).finish();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        });

        binding.textBackground.setBackgroundColor(defaultBackgroundColor);
        binding.itemInfo.setTextColor(defaultTextColor);
        binding.itemTitle.setTextColor(defaultTextColor);
        binding.deezerLogo.setImageTintList(ColorStateList.valueOf(defaultIconColor));

        Glide.with(binding.getRoot().getContext())
                .load(playlist)
                .asBitmap()
                .transcode(transcoder, PaletteBitmap.class)
                .animate(alphaAnimator)
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

            animateTextColor(binding.itemInfo,
                             binding.itemInfo.getCurrentTextColor(),
                             swatch.getTitleTextColor(),
                             animationDuration,
                             interpolator);

            animateTextColor(binding.itemTitle,
                             binding.itemTitle.getCurrentTextColor(),
                             swatch.getTitleTextColor(),
                             animationDuration,
                             interpolator);

            animateBackgroundColor(binding.textBackground,
                                   defaultBackgroundColor,
                                   swatch.getRgb(),
                                   animationDuration,
                                   interpolator);

            animateImageTint(binding.deezerLogo,
                             defaultBackgroundColor,
                             swatch.getBodyTextColor(),
                             animationDuration,
                             interpolator);

        } else {
            binding.itemInfo.setTextColor(swatch.getTitleTextColor());
            binding.itemTitle.setTextColor(swatch.getTitleTextColor());
            binding.textBackground.setBackgroundColor(swatch.getRgb());
            binding.deezerLogo.setImageTintList(ColorStateList.valueOf(swatch.getBodyTextColor()));
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


    @Override public boolean onFailedToRecycleView() {
        binding.image.clearAnimation();
        binding.deezerLogo.clearAnimation();
        binding.textBackground.clearAnimation();
        binding.itemTitle.clearAnimation();
        binding.itemInfo.clearAnimation();
        return true;
    }
}
