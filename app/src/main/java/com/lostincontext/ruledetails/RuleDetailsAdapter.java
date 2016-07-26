package com.lostincontext.ruledetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.ruledetails.items.FenceItem;
import com.lostincontext.ruledetails.items.FenceItemViewHolder;
import com.lostincontext.ruledetails.items.PlaylistInEditScreenViewHolder;
import com.lostincontext.ruledetails.items.PlaylistPicker;
import com.lostincontext.rulescreation.display.RuleCreationItemCallback;

import java.util.List;

public class RuleDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = RuleDetailsAdapter.class.getSimpleName();

    private RuleCreationItemCallback callback;


    List<RuleDetailsItem> items;

    public RuleDetailsAdapter(RuleCreationItemCallback callback) {
        this.callback = callback;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case R.id.view_type_fence_item:
                return FenceItemViewHolder.create(layoutInflater, parent);
            case R.id.view_type_playlist_picker:
                return PlaylistInEditScreenViewHolder.create(layoutInflater, parent);

        }

        throw new RuntimeException("unknown viewType");
    }


    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RuleDetailsItem item = items.get(position);
        switch (item.getItemViewType()) {

            case R.id.view_type_fence_item:
                ((FenceItemViewHolder) holder).bindTo((FenceItem) item);
                break;
            case R.id.view_type_playlist_picker:
                ((PlaylistInEditScreenViewHolder) holder).bindTo((PlaylistPicker) item);
                break;

        }


    }

    @Override public int getItemViewType(int position) {
        return items.get(position).getItemViewType();
    }


    @Override public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setItems(List<RuleDetailsItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

}
