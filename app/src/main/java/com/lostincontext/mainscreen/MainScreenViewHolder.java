package com.lostincontext.mainscreen;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.lostincontext.commons.list.ViewHolder;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.data.rules.FenceIconGiver;
import com.lostincontext.data.rules.Rule;
import com.lostincontext.databinding.ItemRuleBinding;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.getColor;
import static com.lostincontext.utils.Colors.animateBackgroundColor;
import static com.lostincontext.utils.Colors.animateTextColor;

public class MainScreenViewHolder extends ViewHolder implements RequestListener<Playlist, PaletteBitmap> {

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

    public static MainScreenViewHolder create(LayoutInflater layoutInflater,
                                              ViewGroup parent) {
        ItemRuleBinding binding = ItemRuleBinding.inflate(layoutInflater, parent, false);
        return new MainScreenViewHolder(binding);
    }

    public MainScreenViewHolder(final ItemRuleBinding binding) {
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

    public void bindTo(Rule rule) {

        binding.setRule(rule);

        binding.setCallback(new MainScreenViewHolder.Callback() {
            @Override public void onPlaylistCoverClick(Playlist playlist) {
                PlaylistLauncher launcher = new PlaylistLauncher();
                launcher.launchPlaylist(binding.getRoot().getContext(), playlist, false);
            }
        });

        binding.textBackground.setBackgroundColor(defaultBackgroundColor);
        binding.itemTitle.setTextColor(defaultTextColor);

        Glide.with(binding.getRoot().getContext())
                .load(rule.getPlaylist())
                .asBitmap()
                .transcode(transcoder, PaletteBitmap.class)
                .animate(alphaAnimator)
                .listener(this)
                .into(target);

        List<Integer> icons = new ArrayList<>();
        rule.getFenceVM().giveIcon(new FenceIconGiver(), icons);
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
    //endregion requestListener

    private void applyPalette(PaletteBitmap resource, boolean shouldAnimate) {
        Palette palette = resource.palette;
        if (palette == null) return;
        Palette.Swatch swatch = getSwatch(palette);
        if (swatch == null) return;
        if (shouldAnimate) {

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

    @Override public boolean onFailedToRecycleView() {
        binding.image.clearAnimation();
        binding.textBackground.clearAnimation();
        binding.itemTitle.clearAnimation();
        return true;
    }
}
