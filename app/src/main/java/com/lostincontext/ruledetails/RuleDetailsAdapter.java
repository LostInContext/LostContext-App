package com.lostincontext.ruledetails;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.ruledetails.items.FenceItem;
import com.lostincontext.ruledetails.items.FenceItemCallback;
import com.lostincontext.ruledetails.items.FenceItemViewHolder;
import com.lostincontext.ruledetails.items.PlaylistInEditScreenViewHolder;
import com.lostincontext.rulescreation.display.RuleCreationItemCallback;

import java.util.Collections;
import java.util.List;

public class RuleDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = RuleDetailsAdapter.class.getSimpleName();

    private FenceItemCallback callback;


    @NonNull private List<FenceItem> items = Collections.emptyList();
    @Nullable private Playlist playlist;

    public RuleDetailsAdapter(FenceItemCallback callback) {
        this.callback = callback;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case R.id.view_type_fence_item:
                return FenceItemViewHolder.create(layoutInflater, parent, callback);
            case R.id.view_type_playlist_picker:
                return PlaylistInEditScreenViewHolder.create(layoutInflater, parent);
        }

        throw new RuntimeException("unknown viewType");
    }


    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= items.size()) {
            ((PlaylistInEditScreenViewHolder) holder).bindTo(playlist);
            return;
        }

        FenceItem item = items.get(position);
        ((FenceItemViewHolder) holder).bindTo(item);

    }

    @Override public int getItemViewType(int position) {
        int lastIndex = items.size() - 1;
        if (position > lastIndex) return R.id.view_type_playlist_picker;
        return R.id.view_type_fence_item;
    }


    @Override public int getItemCount() {
        int count = items.size();
        if (playlist != null) count++;
        return count;
    }

    public void setItems(@NonNull List<FenceItem> items) {
        if (this.items == items) return;
        this.items = items;
        notifyDataSetChanged();
    }

    public void setPlaylist(Playlist playlist) {
        if (this.playlist == playlist) return;
        boolean insert = this.playlist == null;
        this.playlist = playlist;
        if (insert) notifyItemInserted(items.size());
        else notifyItemChanged(items.size());
    }
}
