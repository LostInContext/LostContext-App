package com.lostincontext.rulescreation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.commons.list.Section;
import com.lostincontext.data.FenceCreator;
import com.lostincontext.databinding.ItemSectionHeaderBinding;
import com.lostincontext.databinding.ItemSectionItemRuleCreationBinding;
import com.lostincontext.rulescreation.display.RuleCreatorViewHolder;
import com.lostincontext.rulescreation.display.RuleCreatorViewHolder.RuleCreationItemCallback;
import com.lostincontext.rulescreation.display.SectionViewHolder;

import java.util.List;

public class RulesCreationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private RuleCreationItemCallback callback;
    int count;

    private List<Section<FenceCreator>> sections;

    public RulesCreationAdapter(RuleCreationItemCallback callback) {

        this.callback = callback;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case R.id.view_type_section_header:
                ItemSectionHeaderBinding headerBinding = ItemSectionHeaderBinding.inflate(layoutInflater,
                                                                                          parent,
                                                                                          false);
                return new SectionViewHolder(headerBinding);

            case R.id.view_type_rule_creator:
                ItemSectionItemRuleCreationBinding itemBinding = ItemSectionItemRuleCreationBinding.inflate(layoutInflater,
                                                                                                            parent,
                                                                                                            false);
                return new RuleCreatorViewHolder(itemBinding);
        }

        return null;
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Section<FenceCreator> section;
        int sectionSize;
        for (int i = 0, sectionsCount = sections.size(); i < sectionsCount; i++) {
            section = sections.get(i);
            sectionSize = section.size();
            if (position > sectionSize) {
                position -= sectionSize;
                continue;
            }
            if (position == 0) {
                //noinspection ConstantConditions
                ((SectionViewHolder) holder).setContent(section);
                break;
            }
            ((RuleCreatorViewHolder) holder).setContent(section.get(position), callback);
            break;
        }

    }

    @Override public int getItemCount() {
        return count;
    }

    public void setSections(List<Section<FenceCreator>> sections) {
        this.sections = sections;
        count();
        notifyDataSetChanged();
    }

    private void count() {
        if (sections == null) {
            this.count = 0;
            return;
        }
        for (int i = 0, size = sections.size(); i < size; i++) {
            count += sections.get(i).size();
        }
    }
}
