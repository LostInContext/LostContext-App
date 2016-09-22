package com.lostincontext.playlists


import android.support.annotation.IdRes
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lostincontext.R
import com.lostincontext.commons.list.*
import com.lostincontext.commons.list.StatefulAdapter.ContentState.*
import com.lostincontext.data.playlist.Playlist

class PlaylistsAdapter(private val itemCallback: PlaylistViewHolder.Callback,
                       private val emptyListCallback: EmptyListCallback,
                       private val infiniteAdapterCallback: InfiniteAdapterCallback)
: StatefulAdapter(LOADING) {

    private var playlists: List<Playlist> = emptyList()
    private var totalNumberOfItems: Int = 0
    private var isLoadingMoreItems = false

    override fun onCreateViewHolder(parent: ViewGroup, @IdRes viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            R.id.view_type_standard -> return PlaylistViewHolder.create(layoutInflater,
                                                                        parent,
                                                                        itemCallback)

            R.id.view_type_loading -> return buildLoadingViewHolder(layoutInflater,
                                                                    parent)

            R.id.view_type_error -> return buildErrorViewHolder(layoutInflater,
                                                                parent,
                                                                emptyListCallback)

            R.id.view_type_empty -> return buildEmptyViewHolder(layoutInflater,
                                                                parent)

            R.id.view_type_load_more -> return createLoadMoreViewHolder(layoutInflater, parent)

            else -> throw IllegalStateException("the adapter is in an invalid state")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
        when (holder.itemViewType) {
            R.id.view_type_standard -> {
                val viewHolder = holder as PlaylistViewHolder
                viewHolder.bindTo(playlists[position])

                if (position > playlists.size - LOAD_MORE_THRESHOLD
                        && canLoadMore()
                        && !isLoadingMoreItems) {
                    isLoadingMoreItems = true
                    infiniteAdapterCallback.onNeedMoreItems()
                }
            }


            R.id.view_type_loading,
            R.id.view_type_load_more,
            R.id.view_type_error, //todo
            R.id.view_type_empty -> {
            }

            else -> throw IllegalStateException("the adapter is in an invalid state")
        }

    }

    override fun getContentItemsCount(): Int = playlists.size + if (canLoadMore()) 1 else 0

    override fun getContentItemViewType(position: Int): Int {
        if (canLoadMore() && position == playlists.size) return R.id.view_type_load_more
        return R.id.view_type_standard
    }

    fun setPlaylists(playlists: List<Playlist>, total: Int) {
        this.totalNumberOfItems = total
        this.playlists = playlists
        if (playlists.size == 0) setCurrentState(EMPTY)
        else setCurrentState(CONTENT, false)
    }

    fun canLoadMore(): Boolean = totalNumberOfItems > playlists.size


    /**
     * swap the current list for a new one and dispatches the notify* calls
     */
    fun swap(newPlaylists: List<Playlist>) {

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                if (oldItemPosition >= playlists.size || newItemPosition >= newPlaylists.size) {
                    return false
                }
                return playlists[oldItemPosition] == newPlaylists[newItemPosition]
            }

            override fun getOldListSize(): Int = itemCount


            override fun getNewListSize(): Int = newPlaylists.size + if (canLoadMore()) 1 else 0

            override fun areContentsTheSame(oldItemPosition: Int,
                                            newItemPosition: Int): Boolean = true

        }, false)
        playlists = newPlaylists
        diffResult.dispatchUpdatesTo(this)
        isLoadingMoreItems = false
    }


    companion object {
        const val LOAD_MORE_THRESHOLD = 5
    }


}
