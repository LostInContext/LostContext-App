package com.lostincontext.playlists


import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.ViewGroup

import com.lostincontext.R
import com.lostincontext.commons.list.EmptyListCallback
import com.lostincontext.commons.list.StatefulAdapter
import com.lostincontext.commons.list.StatefulAdapter.ContentState.LOADING
import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.data.playlist.Playlist

class PlaylistsAdapter(private val itemCallback: PlaylistViewHolder.Callback,
                       private val emptyListCallback: EmptyListCallback)
: StatefulAdapter(LOADING) {

    private var playlists: List<Playlist> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, @IdRes viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            R.id.view_type_standard -> return PlaylistViewHolder.create(layoutInflater,
                                                                        parent,
                                                                        itemCallback)

            R.id.view_type_loading -> return StatefulAdapter.buildLoadingViewHolder(layoutInflater,
                                                                                    parent)

            R.id.view_type_error -> return StatefulAdapter.buildErrorViewHolder(layoutInflater,
                                                                                parent,
                                                                                emptyListCallback)

            R.id.view_type_empty -> return StatefulAdapter.buildEmptyViewHolder(layoutInflater,
                                                                                parent)

            else -> throw IllegalStateException("the adapter is in an invalid state")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        when (holder.itemViewType) {
            R.id.view_type_standard -> {
                val viewHolder = holder as PlaylistViewHolder
                viewHolder.bindTo(playlists[position])
            }


            R.id.view_type_loading,
            R.id.view_type_error, //todo
            R.id.view_type_empty -> {
            }

            else -> throw IllegalStateException("the adapter is in an invalid state")
        }

    }


    override fun getContentItemsCount(): Int {
        return playlists.size
    }


    fun setPlaylists(playlists: List<Playlist>) {
        this.playlists = playlists
        if (playlists.size == 0)
            currentState = StatefulAdapter.ContentState.EMPTY
        else
            currentState = StatefulAdapter.ContentState.CONTENT
    }
}
