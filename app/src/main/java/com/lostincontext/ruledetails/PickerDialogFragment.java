package com.lostincontext.ruledetails;


import android.app.Dialog;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.commons.list.Section;
import com.lostincontext.databinding.RuleDetailsBottomSheetBinding;
import com.lostincontext.ruledetails.pick.RulePickAdapter;

import java.util.List;

public class PickerDialogFragment extends BottomSheetDialogFragment {


    public static PickerDialogFragment newInstance() {
        return new PickerDialogFragment();
    }


    private RulePickAdapter adapter;

    RuleDetailsContract.Presenter callback;

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RuleDetailsBottomSheetBinding binding = DataBindingUtil.inflate(inflater,
                                                                        R.layout.rule_details_bottom_sheet,
                                                                        container,
                                                                        false);

        RecyclerView recyclerView = binding.recyclerView;
        Resources resources = getResources();
        final int span = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), span);
        recyclerView.setLayoutManager(layoutManager);
        int space = resources.getDimensionPixelSize(R.dimen.grid_spacing);
        adapter = new RulePickAdapter(callback);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case R.id.view_type_section_header:
                        return span;
                    case R.id.view_type_grid_bottom_sheet_item:
                        return 1;
                }
                throw new RuntimeException("unhandled view type");
            }
        });


        recyclerView.setAdapter(adapter);

        List<Section> sections = callback.provideFenceChoices();
        adapter.setSections(sections);

        return binding.getRoot();

    }

    public void registerCallback(RuleDetailsContract.Presenter callback) {
        this.callback = callback;
    }

}
