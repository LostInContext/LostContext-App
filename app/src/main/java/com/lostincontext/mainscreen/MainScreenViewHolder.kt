package com.lostincontext.mainscreen

import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat.getColor
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.graphics.Palette
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Interpolator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.animation.ViewPropertyAnimation
import com.bumptech.glide.request.target.Target
import com.lostincontext.PlaylistLauncher
import com.lostincontext.R
import com.lostincontext.commons.images.palette.PaletteBitmap
import com.lostincontext.commons.images.palette.PaletteBitmapTranscoder
import com.lostincontext.commons.images.palette.PaletteImageViewTarget
import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rules.FenceIconGiver
import com.lostincontext.data.rules.Rule
import com.lostincontext.databinding.ItemRuleBinding
import com.lostincontext.utils.Colors.animateBackgroundColor
import com.lostincontext.utils.Colors.animateTextColor

class MainScreenViewHolder(private val binding: ItemRuleBinding) : ViewHolder(binding.root),
                                                                   RequestListener<Playlist, PaletteBitmap> {

    interface Callback {
        fun onPlaylistCoverClick(playlist: Playlist)
    }

    private val target: PaletteImageViewTarget
    private val transcoder: PaletteBitmapTranscoder

    @ColorInt private val defaultTextColor: Int
    @ColorInt private val defaultBackgroundColor: Int
    @ColorInt private val defaultIconColor: Int

    private val animationDuration: Long
    private val interpolator: Interpolator

    internal val alphaAnimator: ViewPropertyAnimation.Animator


    init {
        target = PaletteImageViewTarget(binding.image)
        defaultTextColor = binding.itemTitle.currentTextColor
        val context = binding.root.context
        defaultBackgroundColor = getColor(context,
                                          R.color.playlist_text_default_background)
        defaultIconColor = getColor(context,
                                    R.color.playlist_icon_default_color)

        transcoder = PaletteBitmapTranscoder(context)
        animationDuration = context.resources.getInteger(android.R.integer.config_longAnimTime).toLong()
        interpolator = LinearOutSlowInInterpolator()

        alphaAnimator = ViewPropertyAnimation.Animator { view ->
            view.alpha = 0f
            view.animate().withLayer()
                    .alpha(1f)
                    .setDuration(animationDuration)
                    .setInterpolator(interpolator)
                    .start()
        }
    }

    fun bindTo(rule: Rule) {

        binding.rule = rule
        binding.callback = object : MainScreenViewHolder.Callback {
            override fun onPlaylistCoverClick(playlist: Playlist) {
                val launcher = PlaylistLauncher()
                launcher.launchPlaylist(binding.root.context, playlist, false)
            }
        }


        binding.textBackground.setBackgroundColor(defaultBackgroundColor)
        binding.itemTitle.setTextColor(defaultTextColor)

        Glide.with(binding.root.context)
                .load(rule.playlist)
                .asBitmap()
                .transcode(transcoder, PaletteBitmap::class.java)
                .animate(alphaAnimator)
                .listener(this)
                .into(target)

        val icons = rule.fenceVM.giveIcon(FenceIconGiver())
        if (icons != null && !icons.isEmpty()) {
            if (icons.size >= 3) {
                binding.ic3.setImageResource(icons[2])
            }
            if (icons.size >= 2) {
                binding.ic2.setImageResource(icons[1])
            }
            binding.ic1.setImageResource(icons[0])

        }
    }

    //region requestListener
    override fun onException(e: Exception,
                             model: Playlist,
                             target: Target<PaletteBitmap>,
                             isFirstResource: Boolean): Boolean {
        return false
    }

    override fun onResourceReady(resource: PaletteBitmap,
                                 model: Playlist,
                                 target: Target<PaletteBitmap>,
                                 isFromMemoryCache: Boolean,
                                 isFirstResource: Boolean): Boolean {
        applyPalette(resource, !isFromMemoryCache)
        return false
    }
    //endregion requestListener

    private fun applyPalette(resource: PaletteBitmap, shouldAnimate: Boolean) {
        val palette = resource.palette ?: return
        val swatch = getSwatch(palette) ?: return
        if (shouldAnimate) {

            animateTextColor(binding.itemTitle,
                             binding.itemTitle.currentTextColor,
                             swatch.titleTextColor,
                             animationDuration,
                             interpolator)

            animateBackgroundColor(binding.textBackground,
                                   defaultBackgroundColor,
                                   swatch.rgb,
                                   animationDuration,
                                   interpolator)

        } else {
            binding.itemTitle.setTextColor(swatch.titleTextColor)
            binding.textBackground.setBackgroundColor(swatch.rgb)
        }

    }

    override fun onFailedToRecycleView(): Boolean {
        binding.image.clearAnimation()
        binding.textBackground.clearAnimation()
        binding.itemTitle.clearAnimation()
        return true
    }

    companion object {

        fun create(layoutInflater: LayoutInflater,
                   parent: ViewGroup): MainScreenViewHolder {
            val binding = ItemRuleBinding.inflate(layoutInflater, parent, false)
            return MainScreenViewHolder(binding)
        }

        private fun getSwatch(palette: Palette): Palette.Swatch? {
            var swatch: Palette.Swatch? = palette.vibrantSwatch
            if (swatch == null) swatch = palette.darkVibrantSwatch
            if (swatch == null) swatch = palette.lightVibrantSwatch
            if (swatch == null) {
                val swatches = palette.swatches
                if (swatches.size != 0) swatch = swatches[0]
            }
            return swatch
        }
    }
}
