package com.lostincontext.rulecreate


import android.content.Context
import com.genius.groupie.Item
import com.lostincontext.R
import com.lostincontext.commons.images.ImageGradientTransformation
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.databinding.ItemPlaylistEditScreenBinding
import com.lostincontext.ruledetails.items.PlaylistInEditScreenViewHolder

class PlaylistItem(context: Context, val callback: PlaylistInEditScreenViewHolder.Callback) :
        Item<ItemPlaylistEditScreenBinding>() {


    var playlist: Playlist? = null
        set(value) {
            field = value
            parentDataObserver.onChanged(this)
        }

    val transformation: ImageGradientTransformation


    override fun getLayout() = R.layout.item_playlist_edit_screen

    override fun bind(viewBinding: ItemPlaylistEditScreenBinding, position: Int) {
        viewBinding.playlist = playlist
        viewBinding.callback = callback
        viewBinding.transformation = transformation
    }

    init {
        transformation = ImageGradientTransformation(context)
    }

}
