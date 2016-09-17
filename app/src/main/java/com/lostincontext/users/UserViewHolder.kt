package com.lostincontext.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.user.User
import com.lostincontext.databinding.ItemPlaylistBinding
import com.lostincontext.databinding.ItemUserBinding
import com.lostincontext.playlists.PlaylistViewHolder


class UserViewHolder(val binding: ItemUserBinding,
                     itemCallback: Callback) : ViewHolder(binding.root) {


    init {
        binding.callback = itemCallback
    }

    interface Callback {
        fun onItemClick(user: User)

    }


    fun bindTo(user : User) {
        binding.user = user
    }

    companion object {

        fun create(layoutInflater: LayoutInflater,
                   parent: ViewGroup,
                   itemCallback: UserViewHolder.Callback): UserViewHolder {
            val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
            return UserViewHolder(binding, itemCallback)
        }
    }
}