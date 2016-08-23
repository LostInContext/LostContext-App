package com.lostincontext.commons.list


import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lostincontext.R
import com.lostincontext.commons.list.StatefulAdapter.ContentState.*

abstract class StatefulAdapter(var currentState: StatefulAdapter.ContentState) : Adapter<ViewHolder>() {

    enum class ContentState {
        LOADING,
        CONTENT,
        ERROR,
        EMPTY
    }


    @IdRes
    override fun getItemViewType(position: Int): Int {
        when (currentState) {
            CONTENT -> return getContentItemViewType(position)
            LOADING -> return R.id.view_type_loading
            ERROR -> return R.id.view_type_error
            EMPTY -> return R.id.view_type_empty
        }
    }


    /**
     * override this if you want to handle more than one type of item.
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at position.
     * Type codes need not be contiguous. You are required tu use a resource id
     * @see RecyclerView.Adapter.getItemViewType
     */
    @Suppress("UNUSED_PARAMETER")
    @IdRes
    fun getContentItemViewType(position: Int): Int {
        return R.id.view_type_standard
    }

    override fun getItemCount(): Int {
        when (currentState) {
            CONTENT -> return getContentItemsCount()
            LOADING, ERROR, EMPTY -> return 1
        }
    }

    /**
     * @return the actual number of items in your list for the state [ContentState.CONTENT]
     */
    abstract fun getContentItemsCount(): Int

    /**
     * Set the state and notify about the item changes if there are any.

     * @param state        the [ContentState] to set
     * *
     * @param notifyIfSame will call [Adapter.notifyDataSetChanged] if the current state is
     * *                     already `state`.
     * *
     * @return `true` if the state has changed, `false` otherwise
     */
    fun setCurrentState(state: ContentState, notifyIfSame: Boolean = false): Boolean {
        if (state == currentState) {
            if (notifyIfSame) notifyDataSetChanged()
            return false
        }

        val oldState = currentState
        currentState = state
        if (oldState == CONTENT) {
            notifyDataSetChanged()
        } else {
            notifyItemRemoved(0)
        }
        if (currentState == CONTENT) {
            notifyItemRangeInserted(0, getContentItemsCount())
        } else {
            notifyItemInserted(0)
        }
        return true
    }


    /**
     * builds the default loading view (a spinning loader) and gives it an height equal to the max
     * between the parent height minus `negativeParentHeightOffset` and the item intrinsic height.
     * Returns the associated ViewHolder
     * @param inflater       will be used to inflate the item
     * @param parent         the item's parent RecyclerView
     * @param negativeOffset offset to remove from the parent height in the computation of the item height
     * @return ViewHolder to use in [RecyclerView.Adapter.onCreateViewHolder]
     */
    protected fun buildLoadingViewHolder(inflater: LayoutInflater,
                                         parent: ViewGroup,
                                         negativeOffset: Int = 0): ViewHolder {
        val loadingView = inflater.inflate(R.layout.item_loading, parent, false)
        setItemHeight(parent, loadingView, negativeOffset)
        return DummyViewHolder(loadingView)
    }

    /**
     * builds the default error view and gives it an height equal to the max
     * between the parent height minus `negativeParentHeightOffset` and the item intrinsic height.
     * Returns the associated ViewHolder
     * @param inflater       will be used to inflate the item
     * @param parent         the item's parent RecyclerView
     * @param negativeOffset offset to remove from the parent height in the computation of the item height
     * @param callback       callback for the placeholder's refresh button
     * @return ViewHolder to use in [RecyclerView.Adapter.onCreateViewHolder]
     */
    protected fun buildErrorViewHolder(inflater: LayoutInflater,
                                       parent: ViewGroup,
                                       callback: EmptyListCallback,
                                       negativeOffset: Int = 0): ViewHolder {
        val errorView = inflater.inflate(R.layout.item_error, parent, false)
        setItemHeight(parent, errorView, negativeOffset)
        return ErrorPlaceholderViewHolder(errorView, callback)
    }

    /**
     * builds the default empty view to use when there are no items in the list and gives it an height equal to the max
     * between the parent height minus `negativeParentHeightOffset` and the item intrisic height.
     * Returns the associated ViewHolder
     * @param inflater       will be used to inflate the item
     * @param parent         the item's parent RecyclerView
     * @param negativeOffset offset to remove from the parent height in the computation of the item height
     * @return ViewHolder to use in [RecyclerView.Adapter.onCreateViewHolder]
     */
    protected fun buildEmptyViewHolder(inflater: LayoutInflater,
                                       parent: ViewGroup,
                                       negativeOffset: Int = 0): ViewHolder {
        val emptyView = inflater.inflate(R.layout.item_empty_list, parent, false)
        setItemHeight(parent, emptyView, negativeOffset)
        return DummyViewHolder(emptyView)
    }

    private fun setItemHeight(parent: ViewGroup,
                              itemView: View,
                              negativeOffset: Int) {
        itemView.minimumHeight = Math.max(itemView.height,
                                          parent.height - parent.paddingTop - parent.paddingBottom - negativeOffset)
    }
}
