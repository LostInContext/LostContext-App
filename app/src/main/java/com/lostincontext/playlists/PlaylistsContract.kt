package com.lostincontext.playlists

import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.commons.list.EmptyListCallback
import com.lostincontext.data.playlist.Playlist


object PlaylistsContract {


    const val EXTRA_PLAYLIST = "playlist"

    interface View : BaseView<PlaylistsContract.Presenter> {

        fun setPlaylists(playlists: List<Playlist>)

        fun openDeezerFor(playlist: Playlist)

        fun returnResult(playlist: Playlist)
    }

    interface Presenter : BasePresenter, EmptyListCallback, PlaylistViewHolder.Callback {

        override fun start()

    }
}
