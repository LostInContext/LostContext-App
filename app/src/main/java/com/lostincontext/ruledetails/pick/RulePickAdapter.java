package com.lostincontext.ruledetails.pick;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.commons.list.Adapter;
import com.lostincontext.commons.list.Section;
import com.lostincontext.commons.list.SectionViewHolder;
import com.lostincontext.commons.list.ViewHolder;

import java.util.List;

import static java.util.Collections.emptyList;

public class RulePickAdapter extends Adapter<ViewHolder> {

    private static final String TAG = RulePickAdapter.class.getSimpleName();

    private PickerDialogCallback callback;
    int count;

    private List<Section> sections = emptyList();

    public RulePickAdapter(PickerDialogCallback callback) {
        this.callback = callback;
    }


    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case R.id.view_type_section_header:
                return SectionViewHolder.create(layoutInflater, parent);

            case R.id.view_type_grid_bottom_sheet_item:
                return BottomSheetGridItemViewHolder.create(layoutInflater, parent, callback);

        }

        return null;
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        Section section;
        int sectionSize;
        for (int i = 0, sectionsCount = sections.size(); i < sectionsCount; i++) {
            section = sections.get(i);
            sectionSize = section.size();
            if (position >= sectionSize) {
                position -= sectionSize;
                continue;
            }
            section.onBindViewHolder(holder, position);
            return;
        }

        Log.e(TAG, "onBindViewHolder: ,  position not found ! " + position);

    }

    @Override public int getItemViewType(int position) {
        Section section;
        int sectionSize;
        for (int i = 0, sectionsCount = sections.size(); i < sectionsCount; i++) {
            section = sections.get(i);
            sectionSize = section.size();
            if (position >= sectionSize) {
                position -= sectionSize;
                continue;
            }
            if (position == 0) return section.getHeaderViewType();
            return section.getItemViewType();
        }

        throw new RuntimeException("position not found !");
    }

    @Override public int getItemCount() {
        return count;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
        count();
        notifyDataSetChanged();
    }

    private void count() {
        count = 0;
        for (int i = 0, size = sections.size(); i < size; i++) {
            count += sections.get(i).size();
        }
    }
}
