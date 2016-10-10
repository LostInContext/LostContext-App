package com.lostincontext.rulecreate


import com.genius.groupie.Item
import com.lostincontext.R
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.databinding.ItemPlaylistEditScreenBinding

class PlaylistItem internal constructor(private val playlist: Playlist) : Item<ItemPlaylistEditScreenBinding>() {

    override fun getLayout(): Int {
        return R.layout.item_playlist_edit_screen
    }

    override fun bind(viewBinding: ItemPlaylistEditScreenBinding, position: Int) {
        viewBinding.playlist = playlist
    }
}
