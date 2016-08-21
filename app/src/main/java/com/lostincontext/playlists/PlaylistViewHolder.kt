package com.lostincontext.playlists

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat.getColor
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.graphics.Palette
import android.support.v7.graphics.Palette.Swatch
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Interpolator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.animation.ViewPropertyAnimation
import com.bumptech.glide.request.target.Target
import com.lostincontext.R
import com.lostincontext.commons.images.palette.PaletteBitmap
import com.lostincontext.commons.images.palette.PaletteBitmapTranscoder
import com.lostincontext.commons.images.palette.PaletteImageViewTarget
import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.databinding.ItemPlaylistBinding
import com.lostincontext.utils.animateBackgroundColor
import com.lostincontext.utils.animateImageTint
import com.lostincontext.utils.animateTextColor

class PlaylistViewHolder(private val binding: ItemPlaylistBinding,
                         itemCallback: PlaylistViewHolder.Callback) : ViewHolder(binding.root),
                                                                      RequestListener<Playlist, PaletteBitmap> {


    interface Callback {
        fun onDeezerLogoClick(playlist: Playlist)

        fun onItemClick(playlist: Playlist)
    }

    private val target: PaletteImageViewTarget
    private val transcoder: PaletteBitmapTranscoder

    @ColorInt private val defaultTextColor: Int
    @ColorInt private val defaultBackgroundColor: Int
    @ColorInt private val defaultIconColor: Int

    private val animationDuration: Long
    private val interpolator: Interpolator

    internal var alphaAnimator: ViewPropertyAnimation.Animator

    init {
        binding.callback = itemCallback
        this.target = PaletteImageViewTarget(binding.image)
        this.defaultTextColor = binding.itemTitle.currentTextColor
        val context = binding.root.context
        this.defaultBackgroundColor = getColor(context,
                                               R.color.playlist_text_default_background)
        this.defaultIconColor = getColor(context,
                                         R.color.playlist_icon_default_color)

        this.transcoder = PaletteBitmapTranscoder(context)
        this.animationDuration = context.resources.getInteger(android.R.integer.config_longAnimTime).toLong()
        this.interpolator = LinearOutSlowInInterpolator()

        alphaAnimator = ViewPropertyAnimation.Animator { view ->
            view.alpha = 0f
            view.animate()
                    .withLayer()
                    .alpha(1f)
                    .setDuration(animationDuration)
                    .setInterpolator(interpolator)
                    .start()
        }
    }


    fun bindTo(playlist: Playlist) {

        binding.playlist = playlist

        binding.textBackground.setBackgroundColor(defaultBackgroundColor)
        binding.itemInfo.setTextColor(defaultTextColor)
        binding.itemTitle.setTextColor(defaultTextColor)
        binding.deezerLogo.imageTintList = ColorStateList.valueOf(defaultIconColor)

        Glide.with(binding.root.context)
                .load(playlist)
                .asBitmap()
                .transcode(transcoder, PaletteBitmap::class.java)
                .animate(alphaAnimator)
                .listener(this)
                .into(target)

    }

    //region RequestListener

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

    //endregion


    private fun applyPalette(resource: PaletteBitmap, shouldAnimate: Boolean) {
        val palette = resource.palette ?: return
        val swatch = getSwatch(palette) ?: return
        if (shouldAnimate) {
            binding.itemInfo.animateTextColor(binding.itemInfo.currentTextColor,
                                              swatch.titleTextColor,
                                              animationDuration,
                                              interpolator)

            binding.itemTitle.animateTextColor(binding.itemTitle.currentTextColor,
                                               swatch.titleTextColor,
                                               animationDuration,
                                               interpolator)

            binding.textBackground.animateBackgroundColor(defaultBackgroundColor,
                                                          swatch.rgb,
                                                          animationDuration,
                                                          interpolator)

            binding.deezerLogo.animateImageTint(defaultBackgroundColor,
                                                swatch.bodyTextColor,
                                                animationDuration,
                                                interpolator)

        } else {
            binding.itemInfo.setTextColor(swatch.titleTextColor)
            binding.itemTitle.setTextColor(swatch.titleTextColor)
            binding.textBackground.setBackgroundColor(swatch.rgb)
            binding.deezerLogo.imageTintList = ColorStateList.valueOf(swatch.bodyTextColor)
        }

    }


    override fun onFailedToRecycleView(): Boolean {
        binding.image.clearAnimation()
        binding.deezerLogo.clearAnimation()
        binding.textBackground.clearAnimation()
        binding.itemTitle.clearAnimation()
        binding.itemInfo.clearAnimation()
        return true
    }

    companion object {

        fun create(layoutInflater: LayoutInflater,
                   parent: ViewGroup,
                   itemCallback: Callback): PlaylistViewHolder {
            val binding = ItemPlaylistBinding.inflate(layoutInflater, parent, false)
            return PlaylistViewHolder(binding, itemCallback)
        }

        private fun getSwatch(palette: Palette): Swatch? {
            var swatch = palette.vibrantSwatch
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
