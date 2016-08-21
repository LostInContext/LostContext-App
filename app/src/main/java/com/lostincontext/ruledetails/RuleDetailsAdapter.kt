package com.lostincontext.ruledetails

import android.view.LayoutInflater
import android.view.ViewGroup
import com.lostincontext.R
import com.lostincontext.commons.list.Adapter
import com.lostincontext.commons.list.ViewHolder
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.ruledetails.items.FenceItem
import com.lostincontext.ruledetails.items.FenceItemCallback
import com.lostincontext.ruledetails.items.FenceItemViewHolder
import com.lostincontext.ruledetails.items.PlaylistInEditScreenViewHolder

class RuleDetailsAdapter(private val callback: FenceItemCallback) : Adapter<ViewHolder>() {


    private var items = emptyList<FenceItem>()
    private var playlist: Playlist? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        when (viewType) {
            R.id.view_type_fence_item -> return FenceItemViewHolder.create(layoutInflater,
                                                                           parent,
                                                                           callback)
            R.id.view_type_playlist_picker -> return PlaylistInEditScreenViewHolder.create(
                    layoutInflater,
                    parent)
        }

        throw RuntimeException("unknown viewType")
    }

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int,
                                  payloads: List<Any>) {
        if (position >= items.size) {
            (holder as PlaylistInEditScreenViewHolder).bindTo(playlist!!)
            return
        }

        val item = items[position]
        (holder as FenceItemViewHolder).bindTo(item, payloads)
    }


    override fun getItemViewType(position: Int): Int {
        val lastIndex = items.size - 1
        if (position > lastIndex) return R.id.view_type_playlist_picker
        return R.id.view_type_fence_item
    }


    override fun getItemCount(): Int {
        var count = items.size
        if (playlist != null) count++
        return count
    }

    fun setItems(items: List<FenceItem>) {
        if (this.items === items) return
        this.items = items
        notifyDataSetChanged()
    }

    fun setPlaylist(playlist: Playlist) {
        if (this.playlist == playlist) return
        val insert = this.playlist == null
        this.playlist = playlist
        if (insert)
            notifyItemInserted(items.size)
        else
            notifyItemChanged(items.size)
    }

}
