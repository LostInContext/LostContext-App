package com.lostincontext.commons.list;


import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;

import com.lostincontext.R;
import com.lostincontext.rulescreation.display.SectionViewHolder;

import java.util.List;

public abstract class Section<Model> {

    private String title;
    private List<Model> models;
    private int itemViewType;


    public Section(String title, List<Model> models, int itemViewType) {
        this.title = title;
        this.models = models;
        this.itemViewType = itemViewType;
    }

    @IdRes public int getHeaderViewType() {
        return R.id.view_type_section_header;
    }

    @IdRes public int getItemViewType() {
        return itemViewType;
    }

    public int size() {
        return models.size() + 1;
    }

    public Model get(int i) {
        return models.get(i);
    }

    public String getTitle() {
        return title;
    }


    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((SectionViewHolder) holder).setContent(this);
            return;
        }
        position--;
        onBindItemViewHolder(holder, position);

    }

    protected abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position);
}
