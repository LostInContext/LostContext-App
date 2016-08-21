package com.lostincontext.ruledetails.items


import android.view.LayoutInflater
import android.view.ViewGroup

import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.databinding.ItemPlaylistEditScreenBinding

class PlaylistInEditScreenViewHolder(private val binding: ItemPlaylistEditScreenBinding) : ViewHolder(
        binding.root) {

    fun bindTo(playlist: Playlist) {
        binding.playlist = playlist
    }

    companion object {

        fun create(inflater: LayoutInflater,
                   parent: ViewGroup): PlaylistInEditScreenViewHolder {
            val itemBinding = ItemPlaylistEditScreenBinding.inflate(inflater,
                                                                    parent,
                                                                    false)
            return PlaylistInEditScreenViewHolder(itemBinding)
        }
    }


}
