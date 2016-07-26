package com.lostincontext.commons.list;


import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;

import static com.lostincontext.commons.list.StatefulAdapter.ContentState.CONTENT;

public abstract class StatefulAdapter extends Adapter<ViewHolder> {

    public enum ContentState {
        LOADING,
        CONTENT,
        ERROR,
        EMPTY
    }

    private ContentState mCurrentState;


    public StatefulAdapter(ContentState defaultState) {
        mCurrentState = defaultState;
    }


    @IdRes
    @Override
    public final int getItemViewType(final int position) {
        switch (mCurrentState) {
            case CONTENT:
                return getContentItemViewType(position);
            case LOADING:
                return R.id.view_type_loading;
            case ERROR:
                return R.id.view_type_error;
            case EMPTY:
                return R.id.view_type_empty;
            default:
                throw new IllegalStateException("the adapter is in an invalid state");
        }
    }


    /**
     * override this if you want to handle more than one type of item.
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at position.
     * Type codes need not be contiguous. You are <i>required tu use a resource id</i>
     * @see RecyclerView.Adapter#getItemViewType
     */
    @IdRes
    public int getContentItemViewType(int position) {
        return R.id.view_type_standard;
    }

    @Override
    public final int getItemCount() {
        switch (mCurrentState) {
            case CONTENT:
                return getContentItemsCount();
            case LOADING:
            case ERROR:
            case EMPTY:
                return 1;
            default:
                throw new IllegalStateException("the adapter is in an invalid state");
        }
    }

    /**
     * @return the actual number of items in your list for the state {@link ContentState#CONTENT}
     */
    public abstract int getContentItemsCount();

    /**
     * builds the default loading view (a spinning loader) and gives it an height equal to the max
     * between the parent and the item intrisic height.<br>
     * Returns the associated ViewHolder
     *
     * @param inflater will be used to inflate the item
     * @param parent   the item's parent RecyclerView
     * @return ViewHolder to use in {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     */
    protected static ViewHolder buildLoadingViewHolder(@NonNull LayoutInflater inflater,
                                                       @NonNull ViewGroup parent) {
        return buildLoadingViewHolder(inflater, parent, 0);
    }

    /**
     * builds the default loading view (a spinning loader) and gives it an height equal to the max
     * between the parent height minus {@code negativeParentHeightOffset} and the item intrisic height.<br>
     * Returns the associated ViewHolder
     *
     * @param inflater       will be used to inflate the item
     * @param parent         the item's parent RecyclerView
     * @param negativeOffset offset to remove from the parent height in the computation of the item height
     * @return ViewHolder to use in {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     */
    protected static ViewHolder buildLoadingViewHolder(@NonNull LayoutInflater inflater,
                                                       @NonNull ViewGroup parent,
                                                       int negativeOffset) {
        final View loadingView = inflater.inflate(R.layout.item_loading, parent, false);
        setItemHeight(parent, loadingView, negativeOffset);
        return new DummyViewHolder(loadingView);
    }

    /**
     * builds the default error view and gives it an height equal to the max
     * between the parent height and the item intrisic height.<br>
     * Returns the associated ViewHolder
     *
     * @param inflater will be used to inflate the item
     * @param parent   the item's parent RecyclerView
     * @param callback callback for the placeholder's refresh button
     * @return ViewHolder to use in {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     */
    protected static ViewHolder buildErrorViewHolder(@NonNull LayoutInflater inflater,
                                                     @NonNull ViewGroup parent,
                                                     @NonNull EmptyListCallback callback) {
        return buildErrorViewHolder(inflater, parent, callback, 0);
    }

    /**
     * builds the default error view and gives it an height equal to the max
     * between the parent height minus {@code negativeParentHeightOffset} and the item intrisic height.<br>
     * Returns the associated ViewHolder
     *
     * @param inflater       will be used to inflate the item
     * @param parent         the item's parent RecyclerView
     * @param negativeOffset offset to remove from the parent height in the computation of the item height
     * @param callback       callback for the placeholder's refresh button
     * @return ViewHolder to use in {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     */
    protected static ViewHolder buildErrorViewHolder(@NonNull LayoutInflater inflater,
                                                     @NonNull ViewGroup parent,
                                                     @NonNull EmptyListCallback callback,
                                                     final int negativeOffset) {
        final View errorView = inflater.inflate(R.layout.item_error, parent, false);
        setItemHeight(parent, errorView, negativeOffset);
        return new ErrorPlaceholderViewHolder(errorView, callback);
    }

    /**
     * builds the default empty view to use when there are no items in the list and gives it an height equal to the max
     * between the parent height and the item intrisic height.<br>
     * Returns the associated ViewHolder
     *
     * @param inflater will be used to inflate the item
     * @param parent   the item's parent RecyclerView
     * @return ViewHolder to use in {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     */
    protected static ViewHolder buildEmptyViewHolder(@NonNull LayoutInflater inflater,
                                                     @NonNull ViewGroup parent) {
        return buildEmptyViewHolder(inflater, parent, 0);
    }

    /**
     * builds the default empty view to use when there are no items in the list and gives it an height equal to the max
     * between the parent height minus {@code negativeParentHeightOffset} and the item intrisic height.<br>
     * Returns the associated ViewHolder
     *
     * @param inflater       will be used to inflate the item
     * @param parent         the item's parent RecyclerView
     * @param negativeOffset offset to remove from the parent height in the computation of the item height
     * @return ViewHolder to use in {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     */
    protected static ViewHolder buildEmptyViewHolder(@NonNull LayoutInflater inflater,
                                                     @NonNull ViewGroup parent,
                                                     final int negativeOffset) {
        final View emptyView = inflater.inflate(R.layout.item_empty_list, parent, false);
        setItemHeight(parent, emptyView, negativeOffset);
        return new DummyViewHolder(emptyView);
    }

    private static void setItemHeight(@NonNull ViewGroup parent,
                                      @NonNull View itemView,
                                      int negativeOffset) {
        itemView.setMinimumHeight(Math.max(itemView.getHeight(),
                                           parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom() - negativeOffset));
    }

    public final ContentState getCurrentState() {
        return mCurrentState;
    }

    /**
     * Set the state and notify about the item changes if there are any.
     *
     * @param state the {@link ContentState} to set
     * @return {@code true} if the state has changed, {@code false} otherwise
     */
    public final boolean setCurrentState(ContentState state) {
        if (state == mCurrentState) return false;

        ContentState oldState = mCurrentState;
        mCurrentState = state;
        if (oldState == CONTENT) {
            notifyDataSetChanged();
        } else {
            notifyItemRemoved(0);
        }
        if (mCurrentState == CONTENT) {
            notifyItemRangeInserted(0, getContentItemsCount());
        } else {
            notifyItemInserted(0);
        }
        return true;
    }
}
