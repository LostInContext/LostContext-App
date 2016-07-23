package com.lostincontext.ruledetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.data.FenceCreator;
import com.lostincontext.rulescreation.display.RuleCreationItemCallback;
import com.lostincontext.rulescreation.display.RuleCreatorViewHolder;

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
            case R.id.view_type_rule_creator:
                return RuleCreatorViewHolder.create(layoutInflater, parent, callback);

            case R.id.view_type_plus:
                return PlusButtonViewHolder.create(layoutInflater, parent, callback);

        }

        throw new RuntimeException("unknown viewType");
    }


    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RuleDetailsItem item = items.get(position);
        switch (item.getItemViewType()) {

            case R.id.view_type_plus:
                break;
            case R.id.view_type_rule_creator:
                ((RuleCreatorViewHolder) holder).bindTo((FenceCreator) item);
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
