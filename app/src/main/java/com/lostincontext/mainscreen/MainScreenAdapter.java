package com.lostincontext.mainscreen;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.commons.list.EmptyListCallback;
import com.lostincontext.commons.list.StatefulAdapter;
import com.lostincontext.data.rules.Rule;
import com.lostincontext.databinding.ItemRuleBinding;

import java.util.List;


public class MainScreenAdapter extends StatefulAdapter {

    private List<Rule> rules;
    private EmptyListCallback emptyListCallback;


    public MainScreenAdapter(EmptyListCallback emptyListCallback) {
        super(ContentState.LOADING);
        this.emptyListCallback = emptyListCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, @IdRes int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case R.id.view_type_standard:
                ItemRuleBinding binding = ItemRuleBinding.inflate(layoutInflater, parent, false);
                return new MainScreenViewHolder(binding);

            case R.id.view_type_loading:
                return buildLoadingViewHolder(layoutInflater, parent);

            case R.id.view_type_error:
                return buildErrorViewHolder(layoutInflater, parent, emptyListCallback);

            case R.id.view_type_empty:
                return buildEmptyViewHolder(layoutInflater, parent);

            default:
                throw new IllegalStateException("the adapter is in an invalid state");
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case R.id.view_type_standard:
                MainScreenViewHolder viewHolder = (MainScreenViewHolder) holder;
                viewHolder.setContent(rules.get(position));
                break;


            case R.id.view_type_loading:
            case R.id.view_type_error: //todo
            case R.id.view_type_empty:
                // nothing to do for these
                break;

            default:
                throw new IllegalStateException("the adapter is in an invalid state");
        }

    }

    @Override public int getContentItemsCount() {
        return rules.size();
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
        if (rules.size() == 0) setCurrentState(ContentState.EMPTY);
        else setCurrentState(ContentState.CONTENT);
    }

}
